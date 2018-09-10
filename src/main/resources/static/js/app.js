/**
 * Create stomp websocket
 * And connect to some endpoint over this websocket
 **/
var socket = new SockJS('/message-websocket');
var stompClient = Stomp.over(socket);

var notificationSound = new Audio('/sounds/new-conversation-notification.mp3');
notificationSound.loop = true;
notificationSound.volume = 1;

/**
 * Show sent message on the left
 * It means the message was sent by other user who are talking with you
 **/
function showMessageLeft(m)
{
  var p = document.createElement('p');
  var div = document.createElement('div');
  var element = document.getElementById('messageHistory');
  var h6 = document.createElement('h6');

  h6.innerHTML = JSON.parse(m.body).theName + ':';
  h6.style.fontStyle = 'italic';

  p.appendChild(h6);

  p.classList.add('speech-bubble-left');
  p.classList.add('text-white');
  p.style.padding = '10px';
  p.style.maxWidth = '300px';
  p.style.width = 'auto';
  p.style.float = 'left';
  p.style.display = 'table-row';
  p.style.wordWrap = 'break-word';

  div.style.width = '100%';
  div.style.height = 'auto';
  div.style.display = 'table';

  p.innerHTML += JSON.parse(m.body).content;

  div.appendChild(p);

  element.appendChild(div);

  element.scrollTop = element.scrollHeight;
}

/**
 * Show sent message on the right
 * It means the message was sent by you to other user who are talking with you
 **/
function showMessageRight(m)
{
  var p = document.createElement('p');
  var div = document.createElement('div');
  var element = document.getElementById('messageHistory');
  var h6 = document.createElement('h6');

  h6.innerHTML = JSON.parse(m).theName + ':';
  h6.style.fontStyle = 'italic';

  p.appendChild(h6);

  p.classList.add('speech-bubble');
  p.classList.add('text-white');
  p.style.padding = '10px';
  p.style.maxWidth = '300px';
  p.style.width = 'auto';
  p.style.float = 'right';
  p.style.display = 'table-row';
  p.style.wordWrap = 'break-word';

  div.style.width = '100%';
  div.style.height = 'auto';
  div.style.display = 'table';

  p.innerHTML += JSON.parse(m).messageContent.substring(1);

  div.appendChild(p);

  element.appendChild(div);

  element.scrollTop = element.scrollHeight;
}

/**
 * Send the entered message to other user over websocket
 * The message will be sent to application destination
 * For example in here /app/message
 * Application destination will determine which direction the message should be forwarded
 **/
function sendTheMessage(e)
{
  document.getElementById('messageForm').addEventListener('submit', function (ev) { ev.preventDefault(); });

  var regex = new RegExp('[a-zA-Z0-9]+');
  var theMessage = document.getElementById('idmessage');
  var flag = document.getElementById('sideFlag');

  if((e.keyCode === 13 || e === 13) && theMessage.value !== '' &&
    theMessage.value !== undefined && regex.test(theMessage.value))
  {
    var jsonMessage = JSON.stringify({
      'messageContent': flag.value + theMessage.value,
      'theID': userID.value,
      'theName': userName.value,
      'theConversationID': sessionStorage.getItem('conversationID')
    });

    stompClient.send("/app/message", {}, jsonMessage);

    showMessageRight(jsonMessage);

    theMessage.value = '';
  }
  else if(e.keyCode === 13 || e === 13)
    theMessage.value = '';
}

/**
 * Ajax technique for user status changing operations
 * For example available or busy
 * Post request key as AVAILABLE or BUSY value
 * Page controller will understand the requested operation
 **/
$(function ()
  {
    $('#statusForm').on('submit', function (e)
    {
      e.preventDefault();
    });
    $('#setBusy').click(function ()
    {
      var ds = 'request=BUSY';
      $.ajax({
        type: 'POST',
        url: '/status',
        data: ds,
        success: function (r)
        {
          var statusLabel = document.getElementById('statusLabel');
          statusLabel.innerHTML = 'BUSY';
          statusLabel.className = 'text-danger';

          var setAvailable = document.getElementById('setAvailable');
          setAvailable.disabled = false;

          var setBusy = document.getElementById('setBusy');
          setBusy.disabled = true;
        }
      });
    });
    $('#setAvailable').click(function ()
    {
      var ds = 'request=AVAILABLE';
      $.ajax({
        type: 'POST',
        url: '/status',
        data: ds,
        success: function (r)
        {
          var statusLabel = document.getElementById('statusLabel');
          statusLabel.innerHTML = 'AVAILABLE';
          statusLabel.className = 'text-success';

          var setAvailable = document.getElementById('setAvailable');
          setAvailable.disabled = true;

          var setBusy = document.getElementById('setBusy');
          setBusy.disabled = false;
        }
      });
    });
  }
);

/**
 * Show conversation request notification that was being sent by customer to agent
 **/
function showConvRequest(message)
{
  notificationSound.play();

  var parent = document.getElementById('conversationAlert');

  var div1 = document.createElement('div');
  div1.classList.add('alert');
  div1.classList.add('alert-danger');
  div1.classList.add('blink');
  div1.role = 'alert';
  div1.id = 'alertNotification';

  var form = document.createElement('form');

  var div2 = document.createElement('div');
  div2.classList.add('form-group');

  var strong1 = document.createElement('strong');
  strong1.innerHTML = 'New Conversation!';
  div2.appendChild(strong1);
  form.appendChild(div2);

  var div3 = document.createElement('div');
  div3.classList.add('form-group');
  div3.innerHTML = 'Customer Name: ';
  var strong2 = document.createElement('strong');
  strong2.innerHTML = JSON.parse(message.body).theName;
  div3.appendChild(strong2);
  div3.innerHTML += ' Conversation ID: ';
  var strong3 = document.createElement('strong');
  strong3.innerHTML = JSON.parse(message.body).conversationID;
  div3.appendChild(strong3);
  form.appendChild(div3);

  var div4 = document.createElement('div');
  div4.classList.add('form-group');
  var input1 = document.createElement('input');
  input1.onclick = function () {
    notificationSound.pause();

    stompClient.send("/app/message", {}, JSON.stringify({
      'messageContent': 'CONV_ACCEPT',
      'theID': userID.value,
      'theName': userName.value,
      'theConversationID': JSON.parse(message.body).conversationID
    }));

    sessionStorage.setItem('conversationID', JSON.parse(message.body).conversationID);

    setTimeout(function ()
    {
      location.reload();
    }, 3000);

    window.open('/agent/chat/' + JSON.parse(message.body).conversationID, '_blank');
  };
  input1.classList.add('btn');
  input1.classList.add('btn-primary');
  input1.style.borderRadius = '5px';
  input1.value = 'Accept';
  div4.appendChild(input1);

  div4.append(' ');

  var input2 = document.createElement('input');
  input2.onclick = function () {
    notificationSound.pause();

    stompClient.send("/app/message", {}, JSON.stringify({
      'messageContent': 'CONV_IGNORE',
      'theID': userID.value,
      'theName': userName.value,
      'theConversationID': JSON.parse(message.body).conversationID
    }));

    setTimeout(function ()
    {
      location.reload();
    }, 3000);
  };
  input2.classList.add('btn');
  input2.classList.add('btn-danger');
  input2.style.borderRadius = '5px';
  input2.value = 'Ignore';
  div4.appendChild(input2);
  form.appendChild(div4);

  div1.appendChild(form);
  parent.appendChild(div1);
}

/**
 * After agent action delete the conversation request notification
 **/
function deleteConvRequest()
{
  $(document).ready(
    function ()
    {
      $('#alertNotification').remove();
    }
  );
}

/**
 * For user profile picture upload element
 **/
function readURL(input)
{
  if (input.files && input.files[0])
  {

    var reader = new FileReader();

    reader.onload = function(e)
    {
      $('.image-upload-wrap').hide();

      $('.file-upload-image').attr('src', e.target.result);
      $('.file-upload-content').show();

      $('.image-title').html(input.files[0].name);
    };

    reader.readAsDataURL(input.files[0]);

  }
  else
  {
    removeUpload();
  }
}

function removeUpload()
{
  $('.file-upload-input').replaceWith($('.file-upload-input').clone());
  $('.file-upload-content').hide();
  $('.image-upload-wrap').show();
}
$('.image-upload-wrap').bind('dragover', function (){
  $('.image-upload-wrap').addClass('image-dropping');
});
$('.image-upload-wrap').bind('dragleave', function (){
  $('.image-upload-wrap').removeClass('image-dropping');
});
/*******************************************************************************************/

/**
 * Check whether the add new user form is valid or not
 * If it is valid submit otherwise show messages
 **/
function validateNewUserAndSubmit()
{
  var validationFlag = true;
  var regex = new RegExp('[a-z0-9]+@[a-z]+\\.[a-z]+');

  var element = document.getElementById('userFirstname').value;
  if(element === '')
  {
    validationFlag = false;
    document.getElementById('errorFirstname').innerHTML = 'Please enter user firstname!';
  }
  else
  {
    document.getElementById('errorFirstname').innerHTML = '';
  }

  element = document.getElementById('userLastname').value;
  if(element === '')
  {
    validationFlag = false;
    document.getElementById('errorLastname').innerHTML = 'Please enter user lastname!';
  }
  else
  {
    document.getElementById('errorLastname').innerHTML = '';
  }

  element = document.getElementById('userEmail').value;
  if(element === '' || regex.test(element) === false)
  {
    validationFlag = false;
    document.getElementById('errorEmail').innerHTML = 'Please enter a valid email!';
  }
  else
  {
    document.getElementById('errorEmail').innerHTML = '';
  }

  element = document.getElementById('userGender').value;
  if(element === 'NULL')
  {
    validationFlag = false;
    document.getElementById('errorGender').innerHTML = 'Please select user gender!';
  }
  else
  {
    document.getElementById('errorGender').innerHTML = '';
  }

  element = document.getElementById('userID').value;
  if(element === '')
  {
    validationFlag = false;
    document.getElementById('errorID').innerHTML = 'Please refresh the page in order to generate user ID!';
  }
  else
  {
    document.getElementById('errorID').innerHTML = '';
  }

  element = document.getElementById('userRole').value;
  if(element === 'NULL')
  {
    validationFlag = false;
    document.getElementById('errorRole').innerHTML = 'Please select user role!';
  }
  else
  {
    document.getElementById('errorRole').innerHTML = '';
  }

  element = document.getElementById('username').value;
  if(element === '' || element.length < 5)
  {
    validationFlag = false;
    document.getElementById('errorUsername').innerHTML = 'Please enter at least 5 character username!';
  }
  else
  {
    document.getElementById('errorUsername').innerHTML = '';
  }

  element = document.getElementById('password').value;
  if(element === '' || element.length < 5)
  {
    validationFlag = false;
    document.getElementById('errorPassword').innerHTML = 'Please enter at least 5 character password!';
  }
  else
  {
    document.getElementById('errorPassword').innerHTML = '';
  }

  if(validationFlag === true)
    document.getElementById('newUserForm').submit();
}

/**
 * Show statistics according to labelID
 **/
function showStatistics(labelText, labelID, statisticsID, numberID)
{
  document.getElementById(labelID).innerHTML = labelText + ':';
  document.getElementById(numberID).innerHTML = document.getElementById(statisticsID).value;
}

/**
 * Delete user posting operation
 **/
$(function ()
  {
    $('#deleteUserForm').on('submit', function (e)
    {
      e.preventDefault();
    });
    $('#deleteUser').click(function ()
    {
      var ds = 'userID=' + document.getElementById('userID').value;
      $.ajax({
        type: 'POST',
        url: '/admin/user/delete',
        data: ds,
        success: function (r)
        {
          document.getElementById('deleteAlert').style.visibility = 'visible';

          setTimeout(function ()
          {
            window.close();
          }, 5000);
        },
        error: function (r)
        {
          console.log('ERROR! >>> ' + r);
        }
      });
    });
  }
);

/**
 * Check whether the edit user form is valid or not
 * If it is valid submit otherwise show messages
 **/
function validateEditUserAndSubmit()
{
  var validationFlag = true;
  var regex = new RegExp('[a-z0-9]+@[a-z]+\\.[a-z]+');

  var element = document.getElementById('userFirstname').value;
  if(element === '')
  {
    validationFlag = false;
    document.getElementById('errorFirstname').innerHTML = 'Please enter user firstname!';
  }
  else
  {
    document.getElementById('errorFirstname').innerHTML = '';
  }

  element = document.getElementById('userLastname').value;
  if(element === '')
  {
    validationFlag = false;
    document.getElementById('errorLastname').innerHTML = 'Please enter user lastname!';
  }
  else
  {
    document.getElementById('errorLastname').innerHTML = '';
  }

  element = document.getElementById('userEmail').value;
  if(element === '' || regex.test(element) === false)
  {
    validationFlag = false;
    document.getElementById('errorEmail').innerHTML = 'Please enter a valid email!';
  }
  else
  {
    document.getElementById('errorEmail').innerHTML = '';
  }

  element = document.getElementById('userGender').value;
  if(element === 'NULL')
  {
    validationFlag = false;
    document.getElementById('errorGender').innerHTML = 'Please select user gender!';
  }
  else
  {
    document.getElementById('errorGender').innerHTML = '';
  }

  element = document.getElementById('userID').value;
  if(element === '')
  {
    validationFlag = false;
    document.getElementById('errorID').innerHTML = 'Please refresh the page in order to get the user\'s ID!';
  }
  else
  {
    document.getElementById('errorID').innerHTML = '';
  }

  element = document.getElementById('userRole').value;
  if(element === 'NULL')
  {
    validationFlag = false;
    document.getElementById('errorRole').innerHTML = 'Please select user role!';
  }
  else
  {
    document.getElementById('errorRole').innerHTML = '';
  }

  element = document.getElementById('username').value;
  if(element === '' || element.length < 5)
  {
    validationFlag = false;
    document.getElementById('errorUsername').innerHTML = 'Please enter at least 5 character username!';
  }
  else
  {
    document.getElementById('errorUsername').innerHTML = '';
  }

  if(validationFlag === true)
    document.getElementById('editUserForm').submit();
}

/**
 * Check whether the reset password form is valid or not
 * If it is valid submit otherwise show messages
 **/
function validateResetPasswordAndSubmit()
{
  var element = document.getElementById('password').value;
  if(element === '' || element.length < 5)
  {
    document.getElementById('errorPassword').innerHTML = 'Please enter at least 5 character password!';
  }
  else
  {
    document.getElementById('errorPassword').innerHTML = '';
    document.getElementById('resetPasswordForm').submit();
  }
}
