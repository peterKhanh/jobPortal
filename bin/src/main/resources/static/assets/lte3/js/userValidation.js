// Document is ready 
$(document).ready(function () { 

	// Validate Fullname 
	$("#fullnamecheck").hide(); 
	let fullnameError = true; 
	$("#fullname").keyup(function () { 
		validateFullname(); 
	}); 

	function validateFullname() { 
		let fullnameValue = $("#fullname").val(); 
		if (fullnameValue.length == "") { 
			$("#fullnamecheck").show(); 
			fullnameError = false; 
			return false; 
		} else if (fullnameValue.length < 3 || fullnameValue.length > 30) { 
			$("#fullnamecheck").show(); 
			$("#fullnamecheck").html("**length of fullnameValue must be between 3 and 30"); 
			fullnameError = false; 
			return false; 
		} else { 
			$("#fullnamecheck").hide(); 
			fullnameError = true; 
		} 
	} 


	// Validate Username 
	$("#usercheck").hide(); 
	let usernameError = true; 
	$("#username").keyup(function () { 
		validateUsername(); 
	}); 
	
	// Check Username if exist return true
	function checkUserNameExist() { 
		validUser = false;
		let usernameValue = $("#username").val(); 
		
		$.ajax({
			url:"/register/checkusername/"+usernameValue,
			success:function(response){
				validUser = response;
				if (response == "EXIST"){
					$("#usercheck").show(); 
					$("#usercheck").html("**username exist"); 
					usernameError = false; 					
				}else{
					$("#usercheck").hide(); 
					usernameError = true; 					
				}
			}
		});	

	}
	// Check Email if exist return true
	function checkEmailExist() { 
		validUser = false;
		let emailValue = $("#email").val(); 
		
		$.ajax({
			url:"/register/checkemail/"+emailValue,
			success:function(response){
				validUser = response;
				if (response == "EXIST"){
					$("#emailvalid").show(); 
					$("#emailvalid").html("**Email exist"); 
					emailError = false; 
					return true;					
				}else{
					$("#emailvalid").hide(); 
					emailError = true; 		
					return false;					
			
				}
			}
		});	
	}

	function validateUsername() { 
		let usernameValue = $("#username").val(); 
		if (usernameValue.length == "") { 
			$("#usercheck").show(); 
			usernameError = false; 
			return false; 
		} else if (usernameValue.length < 3 || usernameValue.length > 10) { 
			$("#usercheck").show(); 
			$("#usercheck").html("**length of username must be between 3 and 10"); 
			usernameError = false; 
			return false; 
		} else if (checkUserNameExist()){
				$("#usercheck").show(); 
				$("#usercheck").html("**username exist"); 
				usernameError = false; 
			}else {
			$("#usercheck").hide(); 
			usernameError = true; 
		}
 
	} 


	// Validate Email 
		$("#emailvalid").hide(); 
	let emailError = true; 
	$("#email").keyup(function () { 
		validateEmail(); 
	}); 

	function validateEmail() { 
		const email = document.getElementById("email"); 
		let regex = 
		/^([_\-\.0-9a-zA-Z]+)@([_\-\.0-9a-zA-Z]+)\.([a-zA-Z]){2,7}$/; 
		let s = email.value; 
		if (regex.test(s)) { 
			$("#emailvalid").hide(); 
			emailError = true; 
			
		
			
		} else { 
			emailError = false; 
			$("#emailvalid").show(); 
		} 
		
	}

	// Validate Password 
	$("#passcheck").hide(); 
	let passwordError = true; 
	$("#password").keyup(function () { 
		validatePassword(); 
	}); 
	function validatePassword() { 
		let passwordValue = $("#password").val(); 
		if (passwordValue.length == "") { 
			$("#passcheck").hide(); 
			passwordError = true; 
			return false; 
		}else{ 
		if (passwordValue.length < 3 || passwordValue.length > 10) { 
			$("#passcheck").show(); 
			$("#passcheck").html( 
				"**length of your password must be between 3 and 10"
			); 
			$("#passcheck").css("color", "red"); 
			passwordError = false; 
			return false; 
		} else { 
			$("#passcheck").hide(); 
			passwordError = true;
		} 
	}
		
	} 

	// Validate Confirm Password 
	$("#conpasscheck").hide(); 
	let confirmPasswordError = true; 
	$("#conpassword").keyup(function () { 
		validateConfirmPassword(); 
	}); 
	function validateConfirmPassword() { 
		let confirmPasswordValue = $("#conpassword").val(); 
		let passwordValue = $("#password").val(); 
		if (passwordValue != confirmPasswordValue) { 
			$("#conpasscheck").show(); 
			$("#conpasscheck").html("**Password didn't Match"); 
			$("#conpasscheck").css("color", "red"); 
			confirmPasswordError = false; 
			return false; 
		} else { 
			$("#conpasscheck").hide(); 
			confirmPasswordError = true; 
		} 

	} 

	// Submit button 
	$("#btn_add_user").click(function(e) {
        e.preventDefault();
		validateFullname();
		validateUsername(); 
		validateEmail(); 

		let passwordValue = $("#password").val(); 
		if (passwordValue.length != "") {  
			validatePassword(); 
			validateConfirmPassword(); 
		}
		if (passwordValue.length == "") { 
			$("#passcheck").show(); 
			passwordError = false; 
		//	return false; 
		}
		
		if ( 
			fullnameError == true &&
			usernameError == true && 
		//	passwordError == true && 
		//	confirmPasswordError == true &&
			emailError == true
		) { 	
			$("#frmAddUser").submit();
		} else { 
			return false; 
		} 
	}); 
	
	

	// Submit button 
	$("#btn_update_user").click(function(e) {
        e.preventDefault();
		validateFullname();
		validateUsername(); 
		validateEmail(); 
		let passwordValue = $("#password").val(); 
		if (passwordValue.length != "") {  
			validatePassword(); 
			validateConfirmPassword(); 
		}
		if ( 
			fullnameError == true &&
			usernameError == true && 
		//	passwordError == true && 
		//	confirmPasswordError == true &&
			emailError == true
		) { 	
			$("#frmUpdateUser").submit();
		} else { 
			return false; 
		} 		
	}); 
	

			
$("#btn_register_user").click(function() {
     //   e.preventDefault();
      
 
		validateFullname();
		validateUsername(); 
		validateEmail(); 
		
			if (checkEmailExist()){
				$("#emailvalid").show(); 
				$("#emailvalid").html("**Email exist"); 
				emailError = false; 
			}
		let passwordValue = $("#password").val(); 
		if (passwordValue.length == "") { 
			$("#passcheck").show(); 
			passwordError = false; 
		}else  {  
			validatePassword(); 
			validateConfirmPassword(); 
		}
		if ( 
			fullnameError == true &&
			usernameError == true && 
			passwordError == true && 
			confirmPasswordError == true &&
			emailError == true
		) { 	
		    var form_data = {
			fullName : $("#myModal #fullname").val(),
			email : $("#myModal #email").val(),
			userName : $("#myModal #username").val(),
			passWord : $("#myModal #password").val()
			}

		
			$.ajax({
			url:"/register/newuser",
			data:form_data,
			dataType: "json",
	      	encode: true,
			success:function(response){
				if(response){
					alert(response[1]);					
			   		$("#myModal #fullname").val("");
		       		$("#myModal #email").val("");
		       		$("#myModal #username").val("");
		       		$("#myModal #password").val("");
  					//$("#comment-form #message_send").show();
					
				//	$('.input').val("");
					$('#myModal').modal('toggle'); 
				}
			}
		})	
		} else { 
			return false; 
		} 		
    });

/*

*/
	// Check Email if exist return true
	function checkRegisteredEmail() { 
		// validUser = false;
		emailExist = false; 
		let emailValue = $("#email").val(); 
		
		$.ajax({
			url:"/register/checkemail/"+emailValue,
			success:function(response){
				validUser = response;
				if (response == "EXIST"){
				//	$("#emailvalid").show(); 
					$("#mess").html("**Email Da dang ky"); 
					emailExist = true; 
					//return true;					
				}else{
				//	$("#emailvalid").hide(); 
					$("#mess").html("**Email Chua dang ky"); 

					emailExist = false; 
				}
			}
		});	
		return emailExist;
	}


	// Show Register Form when Click on Register in Menu Bar
	$(".showFormRegister").click(function() {
	   $('#myModal').modal('toggle'); 
	   return false;
	});


	/*
	Click on Reset password button in form 
	System send a email
	*/
	$(".btnForgotPassword").click(function() {
	
		$("#emailvalid").hide(); 
		let emailError = true; 
		$("#email").keyup(function () { 
			validateEmail(); 
		}); 
		
		let emailValue = $("#email").val(); 
		
		$.ajax({
			url:"/register/resetPassword/"+emailValue,
			success:function(response){
				validUser = response;
				if (response == "SEND_MAIL_SUCCESS"){
					$("#mess").html("** Please check your Inbox to Reset the Password"); 
				}else if (response == "SEND_MAIL_FALSE") {
					$("#mess").html("** Sent mail False"); 

					emailExist = false; 
				}else{
					$("#mess").html("**Email Chua dang ky"); 
				}
			}
		});	
	});


	/*
	Click on Change password button in form Reset password
	*/

		$("#btnChangepass").click(function() {
	
		//	$("#mess").html("** Your password was changed"); 
			var emailValue = $("#email").val();
			alert(emailValue);

		let passwordValue = $("#password").val(); 
		if (passwordValue.length == "") { 
			$("#passcheck").show(); 
			passwordError = false; 
		}else  {  
			validatePassword(); 
			validateConfirmPassword(); 
		}
		if ( 
			passwordError == true && 
			confirmPasswordError == true 
		) { 	
			
	    var form_data = {
			passWord : $("#resetPass #password").val()
			}

		$.ajax({
			url:"/register/changePassword/"+emailValue,
			data:form_data,
			dataType: "json",
	      	encode: true,
				success:function(response){
				if (response == "OK"){
					$("#mess").html("** Your password wasdd changed   bbb"); 
					console.log("khanh");
				}else{
					$("#mess").html("**"); 
				}
			}
		});	
}
	});
	
	
		
	
});
