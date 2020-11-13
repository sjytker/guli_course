#!/bin/bash

# start all service with dockers

path=`pwd`
# service="service"
ls $path | while read line
do
	if [ $line == "service" ];
	then
		service_path="`pwd`/service"
		echo $service_path
		cd $service_path
# in each service
		ls $service_path | while read service_name
		do
			dockerFile="`pwd`/$service_name/Dockerfile"
			dockerFolder="`pwd`/$service_name/"
			if [ ! -d $dockerFolder ];then  
				continue    # if not folder, continue
			fi
			echo "$dockerFile"
			#查看镜像id
			IID=$(docker images | grep "$service_name" | awk '{print $3}')
			echo "IID $IID"

			if [ -n "$IID" ]
			then
			    echo "exist $service_name image,IID=$IID, we will delete it first"
			    #删除镜像
			    docker rmi -f $service_name
			    echo "delete $service_name image"
			fi

			#构建该 service 的镜像
			if [ ! -f $dockerFile ];then
				echo "didn't find dockerfile in service $service_name"
			fi
			echo "build $service_name image"
			docker build -t $service_name $dockerFolder
			

			#查看容器id
			CID=$(docker ps -a | grep "$service_name" | awk '{print $1}')
			echo "CID $CID"
			if [ -n "$CID" ]
			then
				echo "exist $service_name container,CID=$CID, we will delete it first"
				#停止
				docker stop $service_name
				#删除容器
				docker rm $service_name
			fi
			#启动
			docker run -d --name $service_name --net=host $service_name
		done
	fi

	if [ $line == "infrastructure" ];
	then
		infrastructure_path="`pwd`/infrastructure_path"
		echo $infrastructure_path
		cd $infrastructure_path
# in each service
		ls $infrastructure_path | while read service_name
		do
			dockerFile="`pwd`/$service_name/Dockerfile"
			dockerFolder="`pwd`/$service_name/"
			if [ ! -d $dockerFolder ];then  
				continue    # if not folder, continue
			fi
			echo "$dockerFile"
			#查看镜像id
			IID=$(docker images | grep "$service_name" | awk '{print $3}')
			echo "IID $IID"

			if [ -n "$IID" ]
			then
			    echo "exist $service_name image,IID=$IID, we will delete it first"
			    #删除镜像
			    docker rmi -f $service_name
			    echo "delete $service_name image"
			fi

			#构建该 service 的镜像
			if [ ! -f $dockerFile ];then
				echo "didn't find dockerfile in service $service_name"
			fi
			echo "build $service_name image"
			docker build -t $service_name $dockerFolder
			

			#查看容器id
			CID=$(docker ps -a | grep "$service_name" | awk '{print $1}')
			echo "CID $CID"
			if [ -n "$CID" ]
			then
				echo "exist $service_name container,CID=$CID, we will delete it first"
				#停止
				docker stop $service_name
				#删除容器
				docker rm $service_name
			fi
			#启动
			docker run -d --name $service_name --net=host $service_name
		done	
	fi
done
