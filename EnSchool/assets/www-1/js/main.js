var avartars = {
    jerry: "jerry.jpg",
    huyou: "huyou.jpg",
    hannah: "hannahca.jpg",
    m1: "1m.jpg" 
};

var contact_data = [];
var individual = [];
var group = [];

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
          var clone = eleData.type == "me" ? $("#chat_me").clone() : $("#chat_you").clone();
          if (typeof  clone.find(".chatTime") != "undefined") {
              clone.find(".chatTime").first().text(eleData.time);
          }
          
          clone.find(".chatItemContent > .avatar").first().attr("src", "./assets/icons/" + avartars[eleData.avatar]);
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
	
}

function onGetUserError(error) {
    //cordova.require("salesforce/util/logger").logToConsole("onErrorSfdc: " + JSON.stringify(error));
    alert('GetUser Error: ' + JSON.stringify(error));
}

$.fn.showChatList = function(data) {
    var contentData =  data.records;
    alert('records ' + JSON.stringify(contentData));
    
    var eleData;
    
    for (var i = 0; i < contentData.length; i++) {
        eleData = contentData[i];
          // Clone li element         
        var clone = $("#test").clone();
        clone.attr("id", eleData.id);
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
        $("#contentList").listview("refresh");
    }
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
    getFeeds();
    return;
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

var onChatSend = function() {
    alert("MESSAGE to SEND: " + $("#chatInputText").val());
      var clone =  $("#chat_me").clone();
          if (typeof  clone.find(".chatTime") != "undefined") {
              clone.find(".chatTime").first().text("time later");
          }
          clone.find("pre").text($("#chatInputText").val());
          clone.find(".chatItemContent > .avatar").first().attr("src", "./assets/icons/jerry.jpg");
          $("#contentChat").append(clone);
    
}

function getUsers() {
	forcetkClient.query("SELECT Name, SmallPhotoUrl FROM User", onGetUserSuccess, onGetUserError); 
}

function getFeeds() {
	alert("getFeeds");
	forcetkClient.query("SELECT Body FROM FeedItem", onGetFeedsSuccess, onGetFeedsError); 
}

function getGroups() {
	alert("getGroups");
	//forcetkClient.query("SELECT CollaborationGroupId from CollaborationGroupMember where Memberid = " + forcetkClient.userId, onSuccess, onError); 
	//forcetkClient.query("SELECT Id, Name from CollaborationGroup where Name = 'Class 123'", onSuccessGroup, onError); 
	forcetkClient.query("SELECT Id, Name from CollaborationGroup where Name = 'Class 123'", onSuccessGroup, onError);
}

function getGroupsAjax() {
	alert("getGroupsAjax");
	forcetkClient.ajax('/v29.0/chatter/users/me/groups', onSuccess, onError); 
}

function postMessage() {
	alert("postMessage");
	var fields = {};
    fields["ParentId"] = forcetkClient.userId;
	//alert(forcetkClient.id);
	//fields["ParentId"] = forcetkClient.getId();
    fields["body"] = "posted on " + new Date();
    forcetkClient.create("FeedItem", fields, onSuccess, onError); 
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



function onGetFeedsSuccess(data) {
	alert('GetFeedsSuccess: ' + JSON.stringify(data));
	
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


$(document).on("pageinit", "#chat_list", initChatList); 
$(document).on("pageinit", "#chat_detail", initChatDetail); 
$(document).on("pageinit", "#new_chat_group", initNewChatGroup);