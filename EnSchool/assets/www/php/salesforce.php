<?php
  
  curl https://na15.salesforce.com/services/oauth2/token -d "grant_type=password" -d  "client_id=3MVG9A2kN3Bn17hupODPXXGeViFuUw9lxOIy1EOHRsJN5IeQbyWYPFsVd.e8iehEd9_vKIbtkkgrU6UGnnRAK" -d "client_secret=4109783661970888937" -d "username=hannah.familyfun@gmail.com" -d "password=1million"



  
  
  
?>
curl -k -d "grant_type=password&clientid=3MVG9A2kN3Bn17hupODPXXGeViFuUw9lxOIy1EOHRsJN5IeQbyWYPFsVd.e8iehEd9_vKIbtkkgrU6UGnnRAK&client_secret=4109783661970888937&username=hannah.familyfun@gmail.com&password=1million" https://na15.salesforce.com/services/oauth2/token


curl https://na15.salesforce.com/services/oauth2/token -d "grant_type=password" -d  "client_id=3MVG9A2kN3Bn17hupODPXXGeViFuUw9lxOIy1EOHRsJN5IeQbyWYPFsVd.e8iehEd9_vKIbtkkgrU6UGnnRAK" -d "client_secret=4109783661970888937" -d "username=hannah.familyfun@gmail.com" -d "password=1millionvUna1bMHd5mKOzAzsPDa5CrgH"




C:\Users\HZHAO>curl https://na15.salesforce.com/services/oauth2/token -d "grant_
type=password" -d  "client_id=3MVG9A2kN3Bn17hupODPXXGeViFuUw9lxOIy1EOHRsJN5IeQby
WYPFsVd.e8iehEd9_vKIbtkkgrU6UGnnRAK" -d "client_secret=4109783661970888937" -d "
username=hannah.familyfun@gmail.com" -d "password=1millionvUna1bMHd5mKOzAzsPDa5C
rgH"
{"id":"https://login.salesforce.com/id/00Di0000000hrZJEAY/005i0000001YgIjAAK","issued_at":"1383982548575","instance_url":"https://na15.salesforce.com","signature":"sDZwhDN/HJwJsNT5DQ3Ht5HkxRwQb7C4ebX/rzT9VJw=","access_token":"00Di0000000hrZJ!AQUAQONbtHFVUMzETHxy3dVBM3U7_pNLXWUMtIg6ERtHzw1n3JKr.bXUJ4I12nGRSvjNKfqSW8plz5fJFyppamJo15dStDPg"}


curl https://na1.salesforce.com/services/data/v20.0/sobjects/Account/ -H "Authorization:Bearer 00Di0000000hrZJ!AQUAQONbtHFVUMzETHxy3dVBM3U7_pNLXWUMtIg6ERtHzw1n3JKr.bXUJ4I12nGRSvjNKfqSW8plz5fJFyppamJo15dStDPg" -H "Content-Type: application/json" -d @newaccount.json"

curl https://na15.salesforce.com/services/data/v20.0/ -H "Authorization: Bearer 00Di0000000hrZJ!AQUAQONbtHFVUMzETHxy3dVBM3U7_pNLXWUMtIg6ERtHzw1n3JKr.bXUJ4I12nGRSvjNKfqSW8plz5fJFyppamJo15dStDPg" -H "X-PrettyPrint:1"