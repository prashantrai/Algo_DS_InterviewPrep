FROM openjdk:8-jdk-alpine
RUN apk --no-cache add curl
RUN apk add --no-cache bash
#ENV CLASSPATH .:.<Any JAR_FILE_PATH Need to run the app>
COPY ./target/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

### Comand To Build Docker Image
docker build -t app .
##Run [User case here is, JAR is taking a file path as command line arg to process]
	##[--net=host here is for accessing Postgres db from diffrent container]
docker run -v <file_path>:/<File_Name_with_ext> --net=host app --file ./<File_name>

Desc: 
	-v <file_path>:/<File_Name_with_ext>  :: This will add the JAR to the container
	--file ./<File_name> :: Reading/provding path to the file copied in container (as mentioned above)
