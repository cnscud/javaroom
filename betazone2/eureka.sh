#!/bin/bash

###
### 修改eureka的instance的metadata.
###
### @author Felix Zhang 2021.6.7
###

# eureka host 服务器地址, 修改为自己的真实地址
eurekaHost='127.0.0.1:8001'


inputready=1

if [ ! -n "$1" ]; then
    echo "command error:serviceName empty!"
    inputready=0
fi

if [ ! -n "$2" ]; then
    echo "command error:serviceIp empty!"
    inputready=0
fi

if [ ! -n "$3" ]; then
    echo "command error:servicePort empty!"
    inputready=0
fi

if [ ! -n "$4" ]; then
    echo "command error:metadata-key empty!"
    inputready=0
fi

if [ ! -n "$5" ]; then
    echo "command error:metadata-value empty!"
    inputready=0
fi

if [ $inputready -eq 0 ]; then
    echo "Command Format: $0 serviceName serviceIp servicePort metadata-key metadata-value"
    echo "Example: $0 serviceName1 192.168.0.105 9001 zone beta"
    exit
fi

#转为小写
serviceName=$(echo "$1" | tr '[:upper:]' '[:lower:]')
hostName=$2
servicePort=$3
metakey=$4
metavalue=$5

# download eureka service list xml
echo 'start download eureka service list xml!'
curl -X GET http://${eurekaHost}/eureka/apps > eureka.xml
if [ $? -eq 0 ]; then
  echo "download eureka service success!"
else
  echo "download eureka service fail!"
fi

findinstance=0
myinstanceId="$hostName:$serviceName:$servicePort"
realinstanceId=""

i=1
while true;
do
  xmllint --xpath "//instance[$i]/app/text()" eureka.xml >> /dev/null 2>&1
  if [ $? -eq 0 ]; then
    appName=$(xmllint --xpath "//instance[$i]/app/text()" eureka.xml)
    lowcaseAppName=$(echo "$appName" | tr '[:upper:]' '[:lower:]')
    ipAddr=$(xmllint --xpath "//instance[$i]/ipAddr/text()" eureka.xml)
    port=$(xmllint --xpath "//instance[$i]/port/text()" eureka.xml)
    instanceId=$(xmllint --xpath "//instance[$i]/instanceId/text()" eureka.xml)
    echo "instance: $appName $ipAddr $port --> $instanceId"

    if [ "$ipAddr:$lowcaseAppName:$port" == "$myinstanceId" ]; then
      findinstance=1
      realinstanceId=$instanceId
    fi
  else
    break
  fi

  #递增
  let i+=1
done

if [ $findinstance -eq 0 ]; then
  echo "not find your instance: $myinstanceId"
  exit
else
  echo "find your instance: $myinstanceId --> $realinstanceId"
fi



curl -X PUT "$eurekaHost/eureka/apps/$serviceName/$realinstanceId/metadata?$metakey=$metavalue"


if [ $? -eq 0 ]; then
  echo "update $serviceName $hostName $metakey:$metavalue success!"
else
  echo "update $serviceName $hostName $metakey:$metavalue fail!"
fi
