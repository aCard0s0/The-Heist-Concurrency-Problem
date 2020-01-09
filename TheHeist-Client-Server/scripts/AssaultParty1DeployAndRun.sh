echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Dércio Bucuane 83457\n"

echo "Compressing data to be sent to the AssaultPartyServer1 side node."
rm -rf AssaultPartyServer1.zip
zip -rq AssaultPartyServer1.zip client configurations entities mainProgram message server sharedRegions

echo "Transfering data to the AssaultPartyServer1 side node."
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'mkdir -p teste/TheHeist'
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'rm -rf teste/TheHeist/*'
sshpass -f password scp AssaultPartyServer1.zip sd0410@l040101-ws09.ua.pt:teste/TheHeist

echo "Decompressing data sent to the AssaultPartyServer1 side node."
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'cd teste/TheHeist ; unzip -q AssaultPartyServer1.zip'

echo "Compiling program files at the AssaultPartyServer1 side node."
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'cd teste/TheHeist ; javac */*.java'

sleep 1

echo "Executing program at the server side node.\n"
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'cd teste/TheHeist ; java server.AssaultParty1Server'
