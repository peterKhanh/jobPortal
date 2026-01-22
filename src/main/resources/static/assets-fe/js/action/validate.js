// Document is ready 
$(document).ready(function() {

	/* Change pass*/
	$(function() {
		$.validator.setDefaults({
			submitHandler: function() {
				$("#frm_changpassword").submit();
			},
		});
		$('#frm_changpassword').validate({
			rules: {
				passWord: {
					required: true,
					remote: "/candidate/checkPassword",

				},

				newPassWord: {
					minlength: 5,
					required: true,
				},
				retypeNewPassWord: {
					equalTo: '#newPassWord'

				},

			},
			messages: {
				passWord: {
					required: "Hãy nhập mật khẩu hiện tại của bạn",
					remote: "Mật khẩu không đúng"
				},

				newPassWord: {
					minlength: "Cần đủ 5 ký tự",
					required: "Hãy nhập mật khẩu mới",
				},
				retypeNewPassWord: {
					equalTo: "mật khẩu chuwa khớp",
				},

			},
			errorElement: 'span',
			errorPlacement: function(error, element) {
				error.addClass('invalid-feedback');
				element.closest('.form-group').append(error);
			},
			highlight: function(element, errorClass, validClass) {
				$(element).addClass('is-invalid');
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).removeClass('is-invalid');
			}
		});
	});


	/*
	Form đăng ký ứng viên
	Thưc hiện kiểm tra thông tin 
	Thực hiên Submit để đăng ký thông tin	
	*/

	$(function() {
		$.validator.setDefaults({
			submitHandler: function() {
				$("#frm_register_new_user").submit();
			}
		});
		$('#frm_register_new_user').validate({
			rules: {
				fullName: {
					required: true,
				},
				address: {
					required: true,
				},

				userName: {
					required: true,
					remote: "/candidate/checkUsername",
				},
				email: {
					required: true,
					email: true,
					remote: "/candidate/checkemail",

				},
				passWord: {
					minlength: 5,
					required: true,
				},
				retypepassword: {
					equalTo: '#passWord'

				},

			},
			messages: {
				fullName: {
					required: "Nhập họ và tên ",
				},
				address: {
					required: "Nhập địa chỉ của bạn",
				},
				email: {
					required: "Nhập email cá nhân",
					email: "Định dạng Email chưa đúng",
					remote: "Email đã sử dụng, hãy nhập email khác",
				},
				userName: {
					required: "Xin vui lòng nhập tên đăng nhập hợp lệ",
					remote: "Tên đăng nhập đã tồn tại",
				},
				passWord: {
					minlength: "Cần đủ 5 ký tự",
					required: "Hãy nhập mật khẩu của bạn",
				},
				retypepassword: {
					equalTo: "Mật khẩu chưa khớp",
				},

			},
			errorElement: 'span',
			errorPlacement: function(error, element) {
				error.addClass('invalid-feedback');
				element.closest('.form-group').append(error);
			},
			highlight: function(element, errorClass, validClass) {
				$(element).addClass('is-invalid');
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).removeClass('is-invalid');
			}
		});
	});


/*
	Form quên mật khẩu 
	Thực hiên Submit để đăng ký thông tin	
	*/

	$(function() {
		$.validator.setDefaults({
			submitHandler: function() {
				$("#frm-forgot-password").submit();
			}
		});
		$('#frm-forgot-password').validate({
			rules: {
				email: {
					required: true,
					email: true,
					remote: "/candidate/checkemail",

				},
			},
			messages: {
				email: {
					required: "Nhập email đã đăng ký",
					email: "Định dạng Email chưa đúng",
					remote: "Email không tòn tại, hãy nhập email khác",
				},

			},
			errorElement: 'span',
			errorPlacement: function(error, element) {
				error.addClass('invalid-feedback');
				element.closest('.form-group').append(error);
			},
			highlight: function(element, errorClass, validClass) {
				$(element).addClass('is-invalid');
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).removeClass('is-invalid');
			}
		});
	});



	/**
		* Bấm nút "Lưu thông tin" trên màn hình sửa thông tin ứng viên"
		*
		*  		
		* 		
		* @return Thông tin ứng viên được lưu lại
		*/

	$(function() {
		$.validator.setDefaults({
			submitHandler: function() {
				$("#frm_edituser").submit();
			}
		});

		$('#frm_edituser').validate({
			rules: {
				fullName: {
					required: true,
				},
				address: {
					required: true,
				},

				userName: {
					required: true,
					remote: "/candidate/checkUsername",
				},
				email: {
					required: true,
					email: true,
					//	remote: "/candidate/checkemail",

				},
				passWord: {
					minlength: 5,
					required: true,
				},
				retypepassword: {
					equalTo: '#passWord'

				},

			},
			messages: {
				fullName: {
					required: "Nhập họ và tên đầy đủ",
				},
				address: {
					required: "Nhập địa chỉ nơi ở",
				},
				email: {
					required: "Nhập email liên hệ",
					email: "Định dạng Email chưa đúng",
					//	remote: "Email đã sử dụng, hãy nhập email khác",
				},
				userName: {
					required: "Xin vui lòng nhập tên đăng nhập hợp lệ",
					remote: "Tên đăng nhập đã tồn tại",
				},
				passWord: {
					minlength: "mật khẩu cần đủ 5 ký tự",
					required: "Hãy nhập mật khẩu của bạn",
				},
				retypepassword: {
					equalTo: "Mật khẩu chưa khớp",
				},

			},
			errorElement: 'span',
			errorPlacement: function(error, element) {
				error.addClass('invalid-feedback');
				element.closest('.form-group').append(error);
			},
			highlight: function(element, errorClass, validClass) {
				$(element).addClass('is-invalid');
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).removeClass('is-invalid');
			}
		});
	});


	$('#btn_addto_favour').on('click', function(e) {
		e.preventDefault();
		$.toast({
			heading: 'Success',
			text: 'Ứng tuyền thành công',
			icon: 'success',
			position: 'top-center'
		})
	});



	/**
		* Bấm nút "Ứng tuyển" khi xem danh sách các Job trên danh sách"
		*
		* @param 
		* 		id the ID of the Job to get
		* 		user ID: ID của User
		* @return Job được ứng tuyển
		*/

	$(".job_apply").click(function() {

		var id = $(this).closest("div").attr("data-id");
		var LoggedIn = $(this).closest("div").attr("user-login");

		if (LoggedIn == "LOGIN") {
			$.ajax({
				url: "/jobapply/apply/" + id,
				success: function(response) {
					//	$("#cart-cnt").html(response[0]);
					//	$("#cart-amt").html(response[1]);
					console.log("Apply: (yes or No)? " + response);
					$.toast({
						heading: 'Success',
						text: 'Ứng tuyển thành công',
						icon: 'success',
						position: 'top-center'
						//	loaderBg: '#9EC600'  // To change the background
					})
				}
			});
		} else {
			//	alert("Chua dang nhap: " + id + "   -- " + LoggedIn)	;
			window.location.href = "/logon";
		}
	})

	/**
	  * Bấm nút "Ứng tuyển ngay" khi xem chi tiet 01 Job"
	  *
	  * @param 
	  * 		id the ID of the Job to get
	  * 		user ID: ID của User
	  * @return Job được ứng tuyển
	  */

	$("#btn_apply_job").click(function() {

		var id = $(this).closest("div").attr("data-id");
		var LoggedIn = $(this).closest("div").attr("user-login");

		if (LoggedIn == "LOGIN") {
			//alert("Xin chào: " + id + "   -- " + LoggedIn)
			$.ajax({
				url: "/jobapply/apply/" + id,
				success: function(response) {
					//					console.log("Apply: (yes or No)? " + response);
					$.toast({
						heading: 'Success',
						text: 'Ứng tuyển thành công',
						icon: 'success',
						position: 'top-center'
						//	loaderBg: '#9EC600'  // To change the background
					})
				}
			});
		} else {
			// Vào trang đăng nhập
			window.location.href = "/logon";
		}
	})




	// This function Validate form Add and Update Enterprise

	$(function() {
		$.validator.setDefaults({
			submitHandler: function() {
				$("#frm_enterprise").submit();
			}
		});
		$('#frm_enterprise').validate({
			rules: {
				name: { required: true, },
				numberOfEmployee: {
					required: true
				},
				address: {
					required: true
					,
				},
				introduction: {
					required: true
				},
			},
			messages: {
				name: "Nhập tên doanh nghiệp tuyển dụng",

				numberOfEmployee: {
					required: "Nhập quy mô công ty",
				},
				address: {
					required: "Nhập địa chỉ trụ sở",
				},
				introduction: {
					required: "Cần nhập giới thiệu công ty",
				},
			},
			errorElement: 'span',
			errorPlacement: function(error, element) {
				error.addClass('invalid-feedback');
				element.closest('.form-group').append(error);
			},
			highlight: function(element, errorClass, validClass) {
				$(element).addClass('is-invalid');
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).removeClass('is-invalid');
			}
		});
	});






});




