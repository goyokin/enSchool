<?php
  $chat = array(
    array(
    "id" => "me",
    "type" => "me",
    "time" => "10:55",
    "avatar" => "huyou",
    "msgType" => "text",
    "nickname" => "hannah",
    "desc" =>"hannah desc",
    "message" => "this is nice i like it day"
    ),
    array(
    "id" => "hannah",
    "type" =>"you",
    "time" => "20:54",
    "avatar" => "hannah",
    "msgType" => "text",
    "nickname" => "hannah",
    "desc" =>"hannah desc",
    "message" => "this is nice no oday"
    ),
    array(
    "id" => "jerry",
    "time" => "13:54",
    "type" => "you",
    "avatar" => "m1",
    "msgType" => "text",
    "nickname" => "hannah",
    "desc" =>"hannah desc",
    "message" => "this is nice last night day"
    )    
  );
    echo json_encode($chat);
?>
