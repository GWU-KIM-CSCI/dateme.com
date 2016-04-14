import bcrypt
import json
import time
import hashlib

from flask import Flask, request, make_response
from pymongo import MongoClient

class MongoDao:
    """Connect to the DB"""
    client = MongoClient()
    db = client.dateme_auth
    user_collection = db.users
    user_collection.create_index('email', unique=True)
    session_collection = db.sessions
    session_collection.create_index('key')

    def userExists(self, email):
        return MongoDao.user_collection.find_one({"email":email}) != None

    def addSession(self, session):
        session_dict = session.asDictionary()
        MongoDao.session_collection.insert_one(session_dict)

    def addUser(self, user_dict):
        MongoDao.user_collection.insert_one(user_dict)

    def getUser(self, email):
        user = MongoDao.user_collection.find_one({"email":email})
        return User(user['email'].encode("utf-8"), user['passhash'].encode("utf-8"), user['role'].encode("utf-8"))


class Session:
    salt = "YkpFHBK4fPnykt#WsGf%8wEzd9tX3Bsy@KqtS59GAGAwV_Q2LqSwPzvGkx_RK-exwEDpm2sdZky-wBf?$=f*rNfZ43-DBQ=gjt&k&6c#ZJB!sN?ANJc#6b6ycR_ua6qC"
    duration = 7 * 24 * 60 * 60 #1 week in seconds

    def __init__(self, email):
        self.email = email
        self.created = int(time.time())
        self.key = hashlib.sha1(Session.salt + self.email + str(self.created)).hexdigest()

    def asDictionary(self):
        return { "email":self.email, "created":self.created, "key":self.key }


class User:
    def __init__(self, email, passhash, role):
        self.email = email
        self.passhash = passhash
        self.role = role 

    def asDictionary(self):
        return { "email":self.email, "role":self.role }


#this should be separate python module not a class
#but for this app to be single file, we'll make it a class
class DateMeAuth:
    USER_ROLE="user"
    ADMIN_ROLE="admin"

    def __init__(self, dateme_auth_dao):
        self.dao = dateme_auth_dao

    def createUser(self, email, password):
        if dao.userExists(email):
            return False
        else:
            user = User(email, USER_ROLE).asDictionary()
            user.passhash = bcrypt.hashpw(password, bcrypt.gensalt())
            dao.addUser(user)
            return True

    def createSession(self, email, password):
        user = dao.getUser(email)
        if (bcrypt.hashpw(password, user.passhash) == user.passhash):
            session = Session(email)
            dao.addSession(session)
            return session
        else:
            raise ValueError("wrong email password combo")

    def checkSession(self, key):
        pass




#create a flask app
app = Flask(__name__)
app.debug = True

dao = MongoDao()
auth = DateMeAuth(dao)

@app.route("/create", methods=["POST"])
def createUser():
    email = request.form['email'].encode("utf-8")
    password = request.form['password'].encode("utf-8")
    if auth.createUser(email, password):
        return make_response(json.dumps({"email":user.email}))
    else:
        return make_response('{"error":"Email already exists in the system"}', 409)

@app.route("/login", methods=["POST"])
def login():
    email = request.form['email'].encode("utf-8")
    password = request.form['password'].encode("utf-8")
    try:
        session = auth.createSession(email, password)
        return make_response(json.dumps(session.asDictionary()))
    except ValueError, err:
        return make_response('{"error":"'+str(err)+'"}', 403)


@app.route("/validate", methods=["GET"])
def validate():
    pass



#start our flask app
if __name__ == "__main__":
    app.run()

