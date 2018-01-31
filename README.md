# Securities averages, etc.

Requires
* Maven 3+
* JDK 1.8

To build, test and produce a runnable jar use `mvn clean package`

To run 

    java -jar code_sample-1.jar [options] <api_key> <ticker_id>...

Note that an api key and at least one ticker ID is required for this application to continue.

Alternatively a Makefile is included that can simplify all of this using `make clean package run`

Options available:

* start - configure the start date in yyyy-mm-dd format (defaults to January 1, 2017)
* end - configure the end date in yyyy-mm-dd format (defaults to June 30, 2017)
* profit - for each security, show the day with the largest profit between daily high and low
* busy - for each security, show the days where the volume was more than 10% above the average
* loser - select the security with the largest number of losing days
* help - the help screen with all of this

E.g., to see the base data for COF, GOOGL and MSFT use

    java -jar code_sample-1.jar ******* COF GOOGL MSFT
    
For a custom date range of the same stock tickers you could use 

    java -jar code_sample-1.jar -start 2017-03-01 -end 2017-08-30 ******* COF GOOGL MSFT
    
Or see all of the extra details with

    java -jar target/code_sample-1.jar -profit -busy -loser ******* GOOGL COF