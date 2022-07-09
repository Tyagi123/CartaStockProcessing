# Carta Stock Process

# How to Run Project 

1. Using Micro service
 Start default Spring boot srever and call below given micro servcie controller 

POST - http://localhost:8080/carta/trade/v1/jobs/processCustomer
Body :
{
"filePath":"/Users/gauravtyagi/Downloads/Python/Java/CartaStockProcessing/src/main/resources/test.csv",
"date" : "2019-01-01",
"fraction" : 3
}


2. Using public static void main class
Run LocalRun class with below given args
<file path> <date> <fraction>
example -  "/Users/gauravtyagi/Downloads/Python/Java/CartaStockProcessing/src/main/resources/test.csv" "2021-01-01" 3


#Note - Part Missing because of time
1. Parllel processing 
2. Exception handling 
3. After batch load we need to validate if some record already process
