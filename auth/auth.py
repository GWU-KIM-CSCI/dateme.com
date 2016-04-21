import time
import bcrypt
import json
import hashlib

from flask import Flask, request, make_response
from pymongo import MongoClient

class MongoDao:
    client = MongoClient()
    db = client.dm_auth
    user_collection = db.users
    user_collection.create_index('email', unique=True)
    key_collection = db.auth_keys
    key_collection.create_index('key', unique=True)
    key_collection.create_index('email')

    def userExists(self, email):
        return MongoDao.user_collection.find_one({"email":email}) != None

    def addAuthKey(self, key):
        MongoDao.key_collection.insert_one(
            {
                "email": key.email,
                "created": key.created,
                "key": key.key
            }
        )


    def createUser(self, user):
        MongoDao.user_collection.insert_one(
            {
                "email": user.email,
                "encpass": user.encpass,
                "role": user.role
            }
        )

    def getAuthKey(self, key):
        key_doc = MongoDao.key_collection.find_one({"key": key})
        if key_doc == None:
            raise ValueError("Key does not exist")
        else:
            return AuthKey(
                key_doc['email'].encode('utf-8'),
                key_doc['created'],
                key_doc['key'].encode('utf-8')
            )
    
    def getUser(self, email):
        user_doc = MongoDao.user_collection.find_one({"email": email})
        return User(
            user_doc['email'].encode('utf-8'),
            user_doc['encpass'].encode('utf-8'),
            user_doc['role'].encode('utf-8')
        )




class AuthKey:
    salt = "3xhk+#&$?@ZR+2t$MFgMgudMM%YF3GE&Mga9H&me9$5fKjA7BGptnyaB7Wwf^Wa-3vqA_Yvah^dJP+dPK$u#!Y@Vu=m$2sMfA3EZLehEaYVb^T?t^2tBWXF@3FtH@Jw@"
    def __init__(self, email, created, key):
        self.email = email
        self.created = created
        self.key = key

    @staticmethod
    def create(user):
        email = user.email
        created = int(time.time())
        key = AuthKey.genKey(email, created)
        return AuthKey(email, created, key)

    @staticmethod
    def genKey(email, created):
        return hashlib.sha1(email + str(created) + AuthKey.salt).hexdigest()



class User:
    def __init__(self, email, encpass, role):
        self.email = email
        self.encpass = encpass
        self.role = role


class DateMeAuth:
    USER_ROLE = "user"
    duration = 7 * 24 * 60 * 60 # 1 week in seconds

    def __init__(self, dm_auth_dao):
        self.dao = dm_auth_dao

    def createUser(self, email, password):
        # check if the email was not already registered
        if (dao.userExists(email)):
            return False
        else:
            encpass = bcrypt.hashpw(password, bcrypt.gensalt())
            user = User(email, encpass, DateMeAuth.USER_ROLE)
            dao.createUser(user)
            return True

    def createAuthKey(self, email, password):
        user = dao.getUser(email)
        if bcrypt.hashpw(password, user.encpass) == user.encpass:
            key = AuthKey.create(user)
            dao.addAuthKey(key)
            return key
        else:
            raise ValueError("Wrong email password combination")

    def validateAuthKey(self, key):
        key_db = dao.getAuthKey(key)
        if key == AuthKey.genKey(key_db.email, key_db.created):
            now = int(time.time())
            if (now - key_db.created) > DateMeAuth.duration:
                return False
            else:
                return True
        else:
            return False


#flask app (web app)
app = Flask(__name__)
app.debug = True

dao = MongoDao()
auth = DateMeAuth(dao)

@app.route("/create", methods=["POST"])
def createUser():
    email = request.form['email'].encode('utf-8')
    password = request.form['password'].encode('utf-8')
    if auth.createUser(email, password):
        return make_response(json.dumps({"email":email}))
    else:
        return make_response('{"error":"Email already exists in the system!"}', 400)

@app.route("/login", methods=["POST"])
def login():
    email = request.form['email'].encode('utf-8')
    password = request.form['password'].encode('utf-8')
    try:
        key = auth.createAuthKey(email, password)
        return make_response(json.dumps({
            "email": key.email,
            "created": key.created,
            "key": key.key
        }))
    except ValueError, err:
        return make_response('{"error":"'+str(err)+'"}', 403)

@app.route("/validate", methods=["GET"])
def validate():
    key = request.args.get('key')
    try:
        if auth.validateAuthKey(key):
            return make_response('{"key":"valid"}')
        else:
            return make_response('{"key":"invalid"}')
    except ValueError, err:
        return make_response('{"key":"invalid"}')


if __name__ == "__main__":
    app.run()
















