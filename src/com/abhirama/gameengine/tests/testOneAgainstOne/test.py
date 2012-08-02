import urllib2
import json

print "Player wants to create an arena\n"
roomCreationURL = "http://localhost:8080/?command=createRoom&playerId=1\n";
print "Fires off request - " + roomCreationURL
response = urllib2.urlopen(roomCreationURL)
roomCreationResponse = response.read()
print "Room creation json response - " + roomCreationResponse + "\n"

print "Another player wants to join the arena created by the previous player\n"
print "Parse the arena creation response and get the room id\n"
roomCreationJSON = json.loads(roomCreationResponse)

roomId = roomCreationJSON['roomId']

print "Arena id created previously is - " + str(roomId) + "\n"

joinRoomURL = "http://localhost:8080/?command=joinRoom&roomId={0}&playerId=2".format(roomId) 

print "Fires off request - " + joinRoomURL + "\n"
response = urllib2.urlopen(joinRoomURL)
joinRoomResponse = response.read()
print "Join arena response is - " + joinRoomResponse + "\n"

print "Now there are two players in the arena\n"

print "Now one player 1 attacks player 2 in the arena\n"
attackURL = "http://localhost:8080/?command=attack&roomId={0}&originatorId=1&targetIds=2".format(roomId)

print "Fires off request - " + attackURL + "\n"
response = urllib2.urlopen(attackURL)
attackResponse = response.read()
print "Attack response is - " + attackResponse
