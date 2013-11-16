<?php

  $me =  array(
  array(
    "id" => "me",
    "time" => "10:54",
    "avatar" => "hannah",
    "msgType" => "text",
    "nickname" => "hannah",
    "desc" =>"hannah desc",
    "idType" =>"individual",
    "unread" => 3
    
  ),
   array(
    "id" => "me",
    "time" => "10:54",
    "avatar" => "huyou",
    "msgType" => "text",
    "nickname" => "hu h 忽悠  you",
    "desc" =>"hannah desc",
    "idType" =>"group",
    "unread" => 0
    
  ),
  array(
    "id" => "me",
    "time" => "10:54",
    "avatar" => "jerry",
    "msgType" => "text",
    "nickname" => "Jerry",
    "desc" =>"KW, come",
    "idType" =>"individual",
    "unread" => 0
  ),
  array(
    "id" => "me",
    "time" => "10:54",
    "avatar" => "m1",
    "msgType" => "text",
    "nickname" => "1M",
    "desc" =>"soup",
    "idType" =>"group",
    "unread" => 1
  )
  );
  
  
  echo json_encode($me);
?>
