""" Just a test file.
Test requests sent from PRM Android for schedule and user
For Help in development
"""

import json
from flask import Flask, request
from flask import Response

app = Flask(__name__)
@app.route('/phoenix/programslot/read',methods=['POST'])
def get_tasks():
    #json_data = jsonify(request.json)
    print(request.json)
    # Get the products in sorted order according to their recommendation in form of json
    json_response = """{ "TestKeyRead" : "TestValueRead"}"""
    return Response(json_response, mimetype='application/json')

@app.route('/phoenix/rest/programslot/create',methods=['PUT'])
def get_tasks2():
    #json_data = jsonify(request.json)
    print(request.json) 
    json_response = """{ "TestKeyCreate" : "TestValueCreate"}"""
    return Response(json_response, mimetype='application/json')

@app.route('/phoenix/rest/programslot/all',methods=['GET'])
def get_tasks3():
    #json_data = jsonify(request.json)
    print(request.json) 
    json_response = """{ "psList": [ { "dateofProgram": "2011-07-14T02:00:00+08:00", "duration": "02:00:00", "programName": "test", "startTime": "2011-07-14T03:00:00+08:00" } ] }"""
    return Response(json_response, mimetype='application/json')

@app.route('/phoenix/rest/programslot/update',methods=['POST'])
def get_tasks4():
    #json_data = jsonify(request.json)
    print(request.json) 
    json_response = """{"ModifyKey":"ModifyValue"}"""
    return Response(json_response, mimetype='application/json')

@app.route('/phoenix/rest/programslot/delete',methods=['DELETE'])
def get_tasks4():
    #json_data = jsonify(request.json)
    print(request.json) 
    json_response = """{"ModifyKey":"ModifyValue"}"""
    return Response(json_response, mimetype='application/json')

if __name__ == '__main__':
    app.run(debug=False,port=9000)