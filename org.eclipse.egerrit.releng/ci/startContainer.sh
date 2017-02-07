# This script starts gerrit on a remote machine and store the port and the container in a file
GERRIT_VERSION=v2.11.5
echo "GV $GERRIT_VERSION"

CONTAINER_ID=$(ssh webmaster@104.154.18.211 docker run -d -p $1:8080 egerrit/gerrit:$GERRIT_VERSION);
echo "$CONTAINER_ID" >> container.id
echo "containerId=$CONTAINER_ID" >> containerId.properties
echo "CID $CONTAINER_ID"

PORT_ID=$(ssh webmaster@104.154.18.211 'bash -s' < getPort.sh $CONTAINER_ID)
echo "$PORT_ID" >> port.id