echo "Apagar info das maquinas"
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'rm -rf teste'
sshpass -f password ssh sd0410@l040101-ws02.ua.pt 'rm -rf teste'
sshpass -f password ssh sd0410@l040101-ws03.ua.pt 'rm -rf teste'
sshpass -f password ssh sd0410@l040101-ws04.ua.pt 'rm -rf teste'
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'rm -rf teste'
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'rm -rf teste'
sshpass -f password ssh sd0410@l040101-ws08.ua.pt 'rm -rf teste'
echo "done"