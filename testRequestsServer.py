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
    json_response = """{ "psList": [ { "dateofProgram": "2011-07-14", "duration": "02:00:00", "programName": "test", "startTime": "03:00:00+08:00" } , { "dateofProgram": "2013-08-15", "duration": "01:00:00", "programName": "test2", "startTime": "03:00:00+08:00" }, { "dateofProgram": "2013-08-16", "duration": "04:00:00", "programName": "test3", "startTime": "03:00:00+08:00" }, { "dateofProgram": "2011-08-14", "duration": "04:00:00", "programName": "test4", "startTime": "03:00:00+08:00" }, { "dateofProgram": "2011-10-14", "duration": "04:00:00", "programName": "test5", "startTime": "03:00:00+08:00" }, { "dateofProgram": "2013-11-14", "duration": "04:00:00", "programName": "test6", "startTime": "03:00:00+08:00" }, { "dateofProgram": "2017-12-14", "duration": "04:00:00", "programName": "test7", "startTime": "03:00:00+08:00" }] }"""
    return Response(json_response, mimetype='application/json')

@app.route('/phoenix/rest/programslot/update',methods=['POST'])
def get_tasks4():
    #json_data = jsonify(request.json)
    print(request.json) 
    json_response = """{"ModifyKey":"ModifyValue"}"""
    return Response(json_response, mimetype='application/json')

@app.route('/phoenix/rest/programslot/delete',methods=['DELETE'])
def get_tasks5():
    #json_data = jsonify(request.json)
    print(request.json) 
    json_response = """{"ModifyKey":"ModifyValue"}"""
    return Response(json_response, mimetype='application/json')

@app.route('/phoenix/rest/programslot/copy',methods=['PUT'])
def get_tasks6():
    #json_data = jsonify(request.json)
    print(request.json) 
    json_response = """{ "TestKeyCopy" : "TestValueCopy"}"""
    return Response(json_response, mimetype='application/json')

@app.route('/phoenix/rest/programslot/modify',methods=['PUT'])
def get_tasks7():
    #json_data = jsonify(request.json)
    print(request.json) 
    json_response = """{ "TestKeyCopy" : "TestValueCopy"}"""
    return Response(json_response, mimetype='application/json')

if __name__ == '__main__':
    app.run(debug=False,port=9000)

    # { "dateofProgram": "2017-12-14T03:00:00+08:00", "duration": "04:00:00", "programName": "test7", "startTime": "2017-12-14T03:00:00+08:00" }
    #json_response = """{ "psList": [ { "dateofProgram": "2011-07-14T02:00:00+08:00", "duration": "02:00:00", "programName": "test", "startTime": "2011-07-14T03:00:00+08:00" } , { "dateofProgram": "2013-08-15T03:00:00+08:00", "duration": "01:00:00", "programName": "test2", "startTime": "2013-08-15T03:00:00+08:00" }, { "dateofProgram": "2013-08-16T03:00:00+08:00", "duration": "04:00:00", "programName": "test3", "startTime": "2013-08-16T03:00:00+08:00" }, { "dateofProgram": "2011-08-14T03:00:00+08:00", "duration": "04:00:00", "programName": "test4", "startTime": "2011-08-14T03:00:00+08:00" }, { "dateofProgram": "2011-10-14T03:00:00+08:00", "duration": "04:00:00", "programName": "test5", "startTime": "2011-10-14T03:00:00+08:00" }, { "dateofProgram": "2013-11-14T03:00:00+08:00", "duration": "04:00:00", "programName": "test6", "startTime": "2013-11-14T03:00:00+08:00" }, { "dateofProgram": "2017-12-14T03:00:00+08:00", "duration": "04:00:00", "programName": "test7", "startTime": "2017-12-14T03:00:00+08:00" }] }"""