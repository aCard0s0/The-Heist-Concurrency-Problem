echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Didércio Bucuane 83457\n"

echo "Compressing data to be sent to the GeneralRepositoryServer side node."
rm -rf GeneralRepositoryServer.zip
zip -rq GeneralRepositoryServer.zip dir_ServerGeneralRepository

echo "Transfering data to the GeneralRepositoryServer side node."
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'rm -f GeneralRepositoryServer.zip'
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'rm -f GenRepoServerSide_com.sh'
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'rm -rf dir_ServerGeneralRepository/'
sshpass -f password scp GeneralRepositoryServer.zip sd0410@l040101-ws01.ua.pt:.

echo "Decompressing data sent to the GeneralRepositoryServer side node."
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'unzip -q GeneralRepositoryServer.zip'

echo "Compiling program files at the GeneralRepositoryServer side node."
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'cd dir_ServerGeneralRepository ; javac */*.java'

echo "Copying file to the directories."
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'mkdir -p /home/sd0410/Public/classes'
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'mkdir -p /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'mkdir -p /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'cp dir_ServerGeneralRepository/interfaces/*.class /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'cp dir_ServerGeneralRepository/structures/*.class /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'cp dir_ServerGeneralRepository/GenRepoServerSide_com.sh /home/sd0410'

sleep 1

echo "Executing program at the server side node.\n"
sshpass -f password ssh sd0410@l040101-ws01.ua.pt './GenRepoServerSide_com.sh'

