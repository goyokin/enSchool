var avartars = {
    jerry: "jerry.jpg",
    huyou: "huyou.jpg",
    hannah: "hannahca.jpg",
    m1: "1m.jpg" 
};

var contact_data = [];
var individual = [];
var group = [];

var users = [];
var groups = [];
var feeds = [];

 
var pictureSource;   // picture source
var destinationType; // sets the format of returned value

//Initialize function

$.fn.positionOn = function(element) {
  return this.each(function() {
    var target   = $(this);
    var position = element.position();
    var triangle = $("#triangle");
    var tri_class_up = "triangle_up";
    var tri_class_down = "triangle_down";
    
    var x   = position.left; 
    var y   = position.top;
    var h   = element.height();
    var w   = element.outerWidth();
    var w_h = $(window).height();
    var h_h = $("#header_nav").height();
    var b   = y + h;
    var t_h = target.height() + triangle.height();
    var tri_x, tri_y;
    
        
    console.log("chat left " + x  + "top: " + y + " height:" + h + "width: " + w + "window height: " + w_h);
    console.log("target height " + t_h );
                                                        
    if ((y - h_h) > t_h) {
     //show on top of the element
     y = y - t_h;   
     triangle.addClass(tri_class_down);
     triangle.removeClass(tri_class_up);  
     tri_y = y + target.height();
     console.log("on top of element");
    } else if ((y+h+t_h) < w_h) {
        // show at the bottom of the element
        triangle.addClass(tri_class_up);
        triangle.removeClass(tri_class_down); 
        tri_y = y+h;
        y = tri_y + 2; 
    } else {
        // show it in the middle of the element
        y = w_h / 2;
        triangle.addClass(tri_class_down);
        triangle.removeClass(tri_class_up);
        tri_y = y + target.height();
    }
    
    console.log("current element top position: " + y + " left: " + x);
    
   /*
    if(align == 'right') {
      x -= (target.outerWidth() - element.outerWidth());
    } else if(align == 'center') {
      x -= target.outerWidth() / 2 - element.outerWidth() / 2;
    }
           */
    x = 20;
    tri_x = position.left + (element.outerWidth() / 3) * 2;
     
    target.css({
      position: 'absolute',
      zIndex:   5000,
      top:      y, 
      left:     x
    });
    
    triangle.css({
        position: 'absolute',
        zIndex: 4000,
        top: tri_y,
        left: tri_x
    }
    );

        target.show();
        triangle.show();
  });
};

$.fn.showChatDetail = function(contentData) {
    console.log(contentData);
    var eleData;
    
    for (var i = 0; i < contentData.length; i++) {
        eleData = contentData[i];
          // Clone li element  
          console.log("eledata type " + eleData.type);
          var clone = eleData.type == "me" ? $("#chat_me").clone() : $("#chat_you_text").clone();
          if (typeof  clone.find(".chatTime") != "undefined") {
              clone.find(".chatTime").first().text(eleData.time);
          }
          
          clone.find("text_you").attr("text",eleData.Body);
          $("#contentChat").append(clone);
          console.log(clone);
    }
        $(".cloudText").click(function(){
                console.log("click"); 
                
            var target = $(this);
            var dialog = $("#chatForwardDialog");
            dialog.positionOn(target);
$("#textinput").css("height", "10px");
            dialog.show(); 
            
            });
};


function onGetUserSuccess(data) {
	$(this).showChatList(data);
	getNewGroups();
	
	
}

function onGetUserError(error) {
    //cordova.require("salesforce/util/logger").logToConsole("onErrorSfdc: " + JSON.stringify(error));
    alert('GetUser Error: ' + JSON.stringify(error));
}


$.fn.showChatList = function(data) {
    var contentData =  data.records;
    alert('records ' + JSON.stringify(contentData));
    
    var eleData;
    
    users = contentData;
    for (var i = 0; i < contentData.length; i++) {
        eleData = contentData[i];
          // Clone li element         
        var clone = $("#test").clone();
        clone.attr("id", eleData.Id);
        clone.find(".li_id").attr("id", eleData.Id);
        
       
        clone.find(":header").html(eleData.Name);
        /*
        clone.find("p").last().html(eleData.desc);
        clone.find("p").first().html(eleData.time);
        */
        clone.find("img").attr("src", eleData.SmallPhotoUrl + "?oauth_token=" + forcetkClient.sessionId);
        /*
        if (eleData.unread > 0) {
            clone.find(".unreadDot").text(eleData.unread);
            clone.find(".unreadDot").show();
        }
        */
        $("#contentList").append(clone);

    }
    
   $("#contentList").listview("refresh");
        
          $("li").click(function(){
          alert($(this).find(".li_id").attr("id"));
            
            });
    return;
    
     contact_data = contentData;
            
            //console.log(data);
              // TODO:: Do your initialization job
            $(".cloudText").click(function(){
                console.log("click"); 
                
            var target = $(this);
            var dialog = $("#chatForwardDialog");
            dialog.positionOn(target);

            dialog.show(); 
            
            });
};


$.fn.showNewChatGroupList = function(contentData) {
    contact_data = contentData;   
    var eleData;
    var hasGroup = false;   
    for (var i = 0; i < contact_data.length; i++) {
        
        eleData = contact_data[i];
        if (eleData.idType == 'individual') {
            console.log("indi");
             var clone = $("#friendDetailLi").clone();
        clone.attr("id", eleData.id);
        clone.find(".nickName").html(eleData.nickname);
        clone.find(".avatar > img").attr("src", "./assets/icons/" + avartars[eleData.avatar]);
        

        
        $("#new_chat_group_conent").append(clone);
         
        } else {
            hasGroup = true;
        }
        
    }
    
    if (group.length > 0) {
        //show select group li
    } 
    $("#friendDetailLi").remove();
   $("#new_chat_group_conent").listview("refresh");   
   
   var scrollEle = $(".selectedListScroll");
   var delta = 40;
   var total = 0;
    $("li").click(function(){
      
      $(this).find(".checkbox  img").toggleClass("checked");
      var ele = $("#new_contact_selected_template").clone();
      
     console.log(  $(this).find(".avatar > img").attr("src"));
      ele.find("img").attr("src", $(this).find(".avatar > img").attr("src"));
      console.log($("#new_chat_group_selectedListScroll").html());
      $("#new_chat_group_selectedListScroll").append(ele);
      
     
      total += delta;
      var left = scrollEle.css("left").replace(/[^-\d\.]/g, '');
      var width = scrollEle.css('width').replace(/[^-\d\.]/g, '');
      
      if (total > scrollEle.width()) {
          var left_width = 160 - width;
        scrollEle.css("width", width + delta + "px"); 
        scrollEle.css("left", left_width + "px");
      }
    }); 
    $(".arrowLeft").click(function(){
        var scrollEle = $(".selectedListScroll");
        var left = scrollEle.css("left").replace(/[^-\d\.]/g, '') + delta;
        console.log("left arrow left : " + left);
        scrollEle.css("left", "0px");
        
        var scrollEle = $(".selectedListScroll")
        /*console.log($(".selectedListScroll").find(".avatar"));
        $(".selectedAvatar").each(function(index){
            console.log("index is " + index);
           var delta = 37; 
           $(this).css("left", "50px");
           console.log($(this).css("left").replace(/[^-\d\.]/g, ''));
           
        });*/
    });
    
        $(".arrowRight").click(function(){
            var scrollEle = $(".selectedListScroll");
            scrollEle.css("left", "-450px");
    });
};

var initChatList = function () {
    console.log("in the init");
		document.addEventListener("deviceready", onDeviceReady,false);
    
    //getUsers();
    /*// populate data
       $.ajax({
          url: "./php/chat_list.php",
        data: "",
        dataType: 'json',
        success: function(data){
            $(this).showChatList(data);
           
        },
        error: function(){
            console.log('Failed to load data');   
        }
    });  */
};

var initChatDetail = function () {
    console.log("in the init chat detail");

    
   // getFeeds();
    return;
    
    
    var data = [
    {
    id:"me",
    type: "me",
    time: "10.55",     
    avatar: "huyou",
    msgType: "text",
    nickname: "hannah",
    desc: "hannah desc",
    message: "this is nice "},
    {
    id:"me",
    type: "you",
    time: "10.55",     
    avatar: "huyou",
    msgType: "text",
    nickname: "hannah",
    desc: "hannah desc",
    message: "this is nice "},
    ];
    $(this).showChatDetail(data);
    // populate data
       $.ajax({
          url: "./php/chat_detail.php",
        data: "",
        dataType: 'json',
        success: function(data){
            $(this).showChatDetail(data);
            //console.log(data);
              // TODO:: Do your initialization job
        
        },
        error: function(){
            console.log('Failed to load data');   
        }
    });  
};

var initNewChatGroup = function() {
    console.log("new chat group");
    if (typeof contacct_data == 'undefined') {
        console.log("need get data");
          $.ajax({
          url: "./php/chat_list.php",
        data: "",
        dataType: 'json',
        success: function(data){
            $(this).showNewChatGroupList(data);
            
            
        },
        error: function(){
            console.log('Failed to load data');   
        }
    });  
    }
}

var onChatPhoto = function() {
    alert("on chat photo select");
    
}
var onChatSend = function() {
    alert("MESSAGE to SEND: " + $("#chatInputText").val());
      var clone =  $("#chat_me").clone();
          if (typeof  clone.find(".chatTime") != "undefined") {
              clone.find(".chatTime").first().text("time later");
          }
          clone.find("pre").text($("#chatInputText").val());
          clone.find(".chatItemContent > .avatar").first().attr("src", "./assets/icons/jerry.jpg");
          $("#contentChat").append(clone);
          postMessage($("#chatInputText").val());
    
}

function getUsers() {
	forcetkClient.query("SELECT Id, Name, SmallPhotoUrl FROM User", onGetUserSuccess, onGetUserError); 
}

function getFeeds() {
	alert("getFeeds");
	//forcetkClient.query("Select Id, Body from FeedItem WHERE CreatedDate > LAST_MONTH ORDER BY CreatedDate DESC, Id DESC LIMIT 20", onGetFeedsSuccess, onGetFeedsError); 

 forcetkClient.query("Select Id, Body, ParentId, CreatedDate from FeedItem WHERE CreatedDate > LAST_MONTH ORDER BY CreatedDate DESC, Id DESC LIMIT 20", onGetFeedsSuccess, onGetFeedsError); 
}

function getFeedsAjax() {
    alert("getFeedsAjax");
    forcetkClient.ajax("/v29.0/chatter/feeds/to/me", onGetFeedsSuccess, onGetFeedsError); 
}

function getGroups() {
	alert("getGroups");
	//forcetkClient.query("SELECT CollaborationGroupId from CollaborationGroupMember where Memberid = " + forcetkClient.userId, onSuccess, onError); 
	//forcetkClient.query("SELECT Id, Name from CollaborationGroup where Name = 'Class 123'", onSuccessGroup, onError); 
	forcetkClient.query("SELECT Id, Name from CollaborationGroup where Name = 'Class 123'", onSuccessGroup, onError);
}

function getNewGroups() {
    alert("getGroups");
    //forcetkClient.query("SELECT CollaborationGroupId from CollaborationGroupMember where Memberid = " + forcetkClient.userId, onSuccess, onError); 
    //forcetkClient.query("SELECT Id, Name, SmallPhotoUrl from CollaborationGroup where Name = 'Class 123'", onSuccessGroup, onError); 
    forcetkClient.query("SELECT Id, Name, SmallPhotoUrl from CollaborationGroup where OwnerId = '" + forcetkClient.userId + "'", onSuccessGroup, onError);
}

function getGroupsAjax() {
	alert("getGroupsAjax");
	forcetkClient.ajax('/v29.0/chatter/users/me/groups', onSuccess, onError); 
}

function postMessage(msg) {
	alert("postMessage");
	var fields = {};
    fields["ParentId"] = forcetkClient.userId;
	//alert(forcetkClient.id);
	//fields["ParentId"] = forcetkClient.getId();
    fields["body"] = msg;
    forcetkClient.create("FeedItem", fields, onPostMessageSuccess, onError); 
}

function onPostMessageSuccess(data) {
	alert(JSON.stringify(data));
}
function postMessageToGroup() {
	alert("postMessageToGroup");
	var fields = {};
	alert("group id = " + forcetkClient.groupId);
    fields["ParentId"] = forcetkClient.groupId;
	//alert(forcetkClient.id);
	//fields["ParentId"] = forcetkClient.getId();
    fields["body"] = "posted to group on " + new Date();
    forcetkClient.create("FeedItem", fields, onSuccess, onError); 
}

function onSuccessGroup(response) {
	alert('Success: ' + JSON.stringify(response));
	groups = response.records;
	
	$(this).showChatList(response);
	
	return;
	var $j = jQuery.noConflict();	
	 $j.each(response.records, function(i, record) {
         currentGroupId = record.Id;
     });
	 forcetkClient.setGroupId(currentGroupId);
	 alert("groupid: " + forcetkClient.groupId);
	 postMessageToGroup();
}

function onSuccess(data) {
	alert('Success: ' + JSON.stringify(data));
	
}


function onError(error) {
    //cordova.require("salesforce/util/logger").logToConsole("onErrorSfdc: " + JSON.stringify(error));
    alert('Error: ' + JSON.stringify(error));
}


function getUserPhotoUrl_orig(userId) {
    //alert(userId);
    for (var i = 0; i < users.length; i++) {
        //alert(users[i]);
        if (users[i] == userId) {
            return users[i+1].SmallPhotoUrl;
        }
    }
    //alert("group length = " + groups.length);
    for (var i = 0; i < groups.length; i++) {
        //alert(users[i]);
        if (groups[i] == userId) {
            return groups[i+1].SmallPhotoUrl;
        }
    }
    return users[1].SmallPhotoUrl;
}

function getUserPhotoUrl(userId) {
    //alert(userId);
    for (var i = 0; i < users.length; i++) {
        //alert(users[i]);
        if (users[i].Id == userId) {
            return users[i].SmallPhotoUrl;
        }
    }
    //alert("group length = " + groups.length);
    for (var i = 0; i < groups.length; i++) {
        //alert(users[i]);
        if (groups[i].Id == userId) {
            return groups[i].SmallPhotoUrl;
        }
    }
    return users[0].SmallPhotoUrl;
}
function onGetFeedsSuccess(data) {
	alert('GetFeedsSuccess: ' + JSON.stringify(data));
	
	
	    var eleData;
	    var contentData = data.records;
    
       for (var i = 0; i < contentData.length; i++) {
        eleData = contentData[i];
          // Clone li element  
          console.log("eledata type " + eleData.type);
          var clone = eleData.type == "me" ? $("#chat_me").clone() : $("#chat_you_text").clone();
          if (typeof  clone.find(".chatTime") != "undefined") {
              clone.find(".chatTime").first().text(eleData.time);
          }
          
          
          
          var photoUrl = getUserPhotoUrl(eleData.ParentId) + "?oauth_token=" + forcetkClient.sessionId;
         
          	clone.find(".chatItemContent > .avatar").first().attr("src", photoUrl);
             clone.find("pre").text(eleData.Body);
          
          $("#contentChat").append(clone);
          console.log(clone);
    }
    
    
        $(".cloudText").click(function(){
                console.log("click"); 
                
            var target = $(this);
            var dialog = $("#chatForwardDialog");
            dialog.positionOn(target);
$("#textinput").css("height", "10px");
            dialog.show(); 
            
            });
	
	
}


function onGetFeedsError(error) {
    //cordova.require("salesforce/util/logger").logToConsole("onErrorSfdc: " + JSON.stringify(error));
    alert('GetFeedError: ' + JSON.stringify(error));
}



    var apiVersion = "v28.0";
    var forcetkClient;
    var debugMode = true;
    var currentUserId;
    var currentGroupId;
    
    //var logToConsole = cordova.require("salesforce/util/logger").logToConsole;

    // When this function is called, Cordova has been initialized and is ready to roll 
    function onDeviceReady() {
        //logToConsole("onDeviceReady: Cordova ready");
		//Call getAuthCredentials to get the initial session credentials
        cordova.require("salesforce/plugin/oauth").getAuthCredentials(salesforceSessionRefreshed, getAuthCredentialsError);

        //register to receive notifications when autoRefreshOnForeground refreshes the sfdc session
        document.addEventListener("salesforceSessionRefresh",salesforceSessionRefreshed,false);
        //Camera
        pictureSource=navigator.camera.PictureSourceType;
        destinationType=navigator.camera.DestinationType;

        //Kate: get all users
        getUsers();
        
        //Kate: get all feeds
        //getFeeds();
        
        //Kate: get all groups
       // getGroups();
        
        //getGroupsAjax();
        
        
        //Kate: post a message. 
        //postMessage();
    }
        

    function salesforceSessionRefreshed(creds) {
        //logToConsole("salesforceSessionRefreshed");
        
        // Depending on how we come into this method, `creds` may be callback data from the auth
        // plugin, or an event fired from the plugin.  The data is different between the two.
        var credsData = creds;
        if (creds.data)  // Event sets the `data` object with the auth data.
            credsData = creds.data;

        forcetkClient = new forcetk.Client(credsData.clientId, credsData.loginUrl, null,
            cordova.require("salesforce/plugin/oauth").forcetkRefresh);
        forcetkClient.setSessionToken(credsData.accessToken, apiVersion, credsData.instanceUrl);
        forcetkClient.setRefreshToken(credsData.refreshToken);
        forcetkClient.setUserAgentString(credsData.userAgent);
        forcetkClient.setUserId(credsData.userId);
        currentUserId = credsData.userId;
        //alert("forcetkClient userid: " + forcetkClient.userId);
    }

    function getAuthCredentialsError(error) {
        //logToConsole("getAuthCredentialsError: " + error);
    }

/*
    function onCameraClick() {
        alert("camera click");
    }
    function onPhotoClick() {
        alert("Photo click");
    }
    
    */
    
    // Called when a photo is successfully retrieved
    //
    function onPhotoDataSuccess(imageData) {
      onPhotoSuccess("data:image/jpeg;base64," + imageData);
      
    }

    // Called when a photo is successfully retrieved
    //

function onPhotoSuccess(data) {
  alert("MESSAGE to SEND: ");
      var clone =  $("#chat_me_photo").clone();
          if (typeof  clone.find(".chatTime") != "undefined") {
              clone.find(".chatTime").first().text("time later");
          }
alert(clone.find("#smallImage").attr("src"));
clone.find("#smallImage").attr("src", data);

          $("#contentChat").append(clone);
          $('#popupBasic').popup("close");
          $('#popupBasic').hide();
                    return;
}

    function onPhotoURISuccess(imageURI) {
      onPhotoSuccess(imageURI);
      
    }

    // A button will call this function
    //
    function capturePhoto() {
      // Take picture using device camera and retrieve image as base64-encoded string
      navigator.camera.getPicture(onPhotoDataSuccess, onFail, { quality: 50,
        destinationType: destinationType.DATA_URL });
    }

    // A button will call this function
    //
    function capturePhotoEdit() {
      // Take picture using device camera, allow edit, and retrieve image as base64-encoded string
      navigator.camera.getPicture(onPhotoDataSuccess, onFail, { quality: 20, allowEdit: true,
      destinationType: destinationType.DATA_URL });
    }

    // A button will call this function
    //
    function getPhoto(source) {
      // Retrieve image file location from specified source
      navigator.camera.getPicture(onPhotoURISuccess, onFail, { quality: 50,
        destinationType: destinationType.FILE_URI,
        sourceType: source });
    }

    // Called if something bad happens.
    //
    function onFail(message) {
      alert('Failed because: ' + message);
    }


    function onCameraClick() {
        capturePhoto();

    }
    function onPhotoClick() {
        getPhoto(pictureSource.PHOTOLIBRARY);
        //getPhoto(pictureSource.SAVEDPHOTOALBUM);
    }
    
$( "#popupPanel" ).on({
    popupbeforeposition: function() {
        var h = $( window ).height();
        alert("window heihgt" + h);
 
        $( "#popupPanel" ).css( "height", h );
    }
});    

$("#pic_camera").bind("click", function(){onCameraClick();});
$("#pic_album").bind("click", function(){onPhotoClick();});
$("#pic_cancel").bind("click", function(){$('#popupBasic').popup('close');});

$(document).on("pageinit", "#chat_list", initChatList); 
$(document).on("pageinit", "#chat_detail", initChatDetail); 
$(document).on("pageinit", "#new_chat_group", initNewChatGroup);