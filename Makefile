clean:
	mvn clean

install:
	mvn install

package:
	mvn package

run:
	java -jar target/code_sample-1.jar -start 2017-10-01 -end 2017-12-31 -profit -loser -busy s-GMZ_xkw6CrkGYUWs1p GOOGL COF MSFT

