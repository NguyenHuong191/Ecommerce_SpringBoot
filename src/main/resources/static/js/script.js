$(function(){
	
	//user register
	var $userRegister = $("#userRegister");
	
	$userRegister.validate({
		rules:{
			name:{
				required: true,
				lettersonly: true
			},
			email: {
				required: true,
				space: true,
				email: true
			},
			phone: {
				required: true,
				space: true,
				phoneOnly: true,
				minlength: 9,
				maxlength: 9
			},
			password: {
				required: true, 
				space: true,
				pass: true,
				minlength: 8,
				maxlength: 20
			},
			confirmpassword: {
				required: true, 
				space: true, 
				pass: true,
				minlength: 8,
				maxlength: 20,
				equalTo: '#pass'
			},
			address: {
				required: true, 
				all: true
			},
			city: {
				required: true, 
				lettersonlyNoUnderline: true
			},
			state: {
				required: true, 
				lettersonlyNoUnderline: true
			},
			pincode: {
				required: true, 
				space: true,
				numericOnly: true
			}
		},
		messages:{
			name:{
				required: 'Name is required!',
				lettersonly: 'Invalid name!'
			},
			email:{
				required: 'Email is required!',
				space: 'Space is not allowed!',
				email: 'Invalid email!'
			},
			phone: {
				required: 'Mobile phone is required!',
				space: 'Space is not allowed!',
				phoneOnly: 'Phone must start with 0 and contain 9 digits!',
				minlength: 'Length must be 9',
				maxlength: 'Length must be 9' 
			},
			password: {
				required: 'Password is required!', 
				space: 'Space is not allowed!',
				pass: 'At least 1 number, 1 lowercase, 1 uppercase, 1 special character',
				minlength: 'Length must be 8',
			    maxlength: 'Length must less 20' 
			},
			confirmpassword: {
				required: 'Please confirm your password!', 
				space: 'Space is not allowed!', 
				pass: 'At least 1 number, 1 lowercase, 1 uppercase, 1 special character',
				minlength: 'Length must be 8',
			    maxlength: 'Length must less 20' ,
				equalTo: 'Password do not match!'
			},
		    address: {
		    	required: 'Address is required!', 
				all: 'Invalid address'
			},
			city: {
		     	required: 'City is required!', 
				lettersonlyNoUnderline: 'Only letters!'
			},
			state: {
				required: 'State is required!', 
				lettersonlyNoUnderline: 'Only letters!'
			},
			pincode: {
				required: 'Pincode is required!', 
				space: 'Space is not allowed!',
			    numericOnly: 'Only number is allowed!'
			}
		}
	})	
	
	//orders
	var $orders = $("#orders");
		
		$orders.validate({
			rules:{
				name:{
					required: true,
					lettersonly: true
				},
				email: {
					required: true,
					space: true,
					email: true
				},
				phone: {
					required: true,
					space: true,
					phoneOnly: true,
					minlength: 9,
					maxlength: 9
				},
				address: {
					required: true, 
					all: true
				},
				city: {
					required: true, 
					lettersonlyNoUnderline: true
				},
				state: {
					required: true, 
					lettersonlyNoUnderline: true
				}
			},
			messages:{
				name:{
					required: 'Name is required!',
					lettersonly: 'Invalid name!'
				},
				email:{
					required: 'Email is required!',
					space: 'Space is not allowed!',
					email: 'Invalid email!'
				},
				phone: {
					required: 'Mobile phone is required!',
					space: 'Space is not allowed!',
					phoneOnly: 'Phone must start with 0 and contain 9 digits!',
					minlength: 'Length must be 9',
					maxlength: 'Length must be 9' 
				},
			    address: {
			    	required: 'Address is required!', 
					all: 'Invalid address'
				},
				city: {
			     	required: 'City is required!', 
					lettersonlyNoUnderline: 'Only letters!'
				},
				state: {
					required: 'State is required!', 
					lettersonlyNoUnderline: 'Only letters!'
				}
			}
		})

		//change profile
		var $changeProfile = $("#changeProfile");
			
		$changeProfile.validate({
				rules:{
					name:{
						required: true,
						lettersonly: true
					},
					phone: {
						required: true,
						space: true,
						phoneOnly: true,
						minlength: 9,
						maxlength: 9
					},
					address: {
						required: true, 
						all: true
					},
					city: {
						required: true, 
						lettersonlyNoUnderline: true
					},
					state: {
						required: true, 
						lettersonlyNoUnderline: true
					},
					pincode: {
						required: true, 
						space: true,
						numericOnly: true
					}
				},
				messages:{
					name:{
						required: 'Name is required!',
						lettersonly: 'Invalid name!'
					},
					phone: {
						required: 'Mobile phone is required!',
						space: 'Space is not allowed!',
						phoneOnly: 'Phone must start with 0 and contain 9 digits!',
						minlength: 'Length must be 9',
						maxlength: 'Length must be 9' 
					},
				    address: {
				    	required: 'Address is required!', 
						all: 'Invalid address'
					},
					city: {
				     	required: 'City is required!', 
						lettersonlyNoUnderline: 'Only letters!'
					},
					state: {
						required: 'State is required!', 
						lettersonlyNoUnderline: 'Only letters!'
					},
					pincode: {
						required: 'Pincode is required!', 
						space: 'Space is not allowed!',
					    numericOnly: 'Only number is allowed!'
					}
				}
			})		
			
    //change password
	var $changePassword = $("#changePassword");
			
		$changePassword.validate({
			rules:{
				currentPass:{
					required: true, 
					space: true,
					pass: true,
					minlength: 8,
					maxlength: 20
				},
			  	newPass:{
					required: true, 
					space: true,
					pass: true,
					minlength: 8,
					maxlength: 20
				},
				confirmPass:{
					required: true, 
					space: true, 
					pass: true,
					minlength: 8,
					maxlength: 20,
					equalTo: '#pass'
			    }
			},
			messages:{
				currentPass:{
					required: 'Password is required!', 
					space: 'Space is not allowed!',
					pass: 'At least 1 number, 1 lowercase, 1 uppercase, 1 special character',
					minlength: 'Length must be 8',
				    maxlength: 'Length much less 20' 
				},
				newPass:{
					required: 'Password is required!', 
					space: 'Space is not allowed!',
					pass: 'At least 1 number, 1 lowercase, 1 uppercase, 1 special character',
					minlength: 'Length must be 8',
					 maxlength: 'Length must less 20' 
				},
				confirmPass:{
					required: 'Password is required!', 
					space: 'Space is not allowed!',
					pass: 'At least 1 number, 1 lowercase, 1 uppercase, 1 special character',
					minlength: 'Length must be 8',
					maxlength: 'Length must less 20' , 
					equalTo: 'Password do not match!'
				}
			}	
	    })
})

jQuery.validator.addMethod('lettersonly', function(value, element){
	return /^[\u00C0-\u1EF9\u0102\u0103\u00E2\u00E3\u1EA0-\u1EF9\u01A0\u01B0a-zA-Z\s-]+$/.test(value);  // Chấp nhận tiếng Việt và các ký tự La-tinh
});
jQuery.validator.addMethod('lettersonlyNoUnderline', function(value, element){
	return /^[\u00C0-\u017F\u1EA0-\u1EF9a-zA-Z\s]+$/.test(value);
});
jQuery.validator.addMethod('space', function(value, element){
	return /^[^-\s]+$/.test(value);
});
jQuery.validator.addMethod('pass', function(value, element){
	return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#])[A-Za-z\d@#]+$/.test(value);
});
jQuery.validator.addMethod('all', function(value, element){
	return /^[0-9\u00C0-\u1EF9\u0102\u0103\u00E2\u00E3\u1EA0-\u1EF9\u01A0\u01B0a-zA-Z0-9_,.\s-]+$/.test(value);  // Bao gồm số, tiếng Việt, chữ cái, dấu phẩy, dấu chấm, khoảng trắng và dấu gạch ngang
});
jQuery.validator.addMethod('phoneOnly', function(value, element){
	return /^0[0-9]+$/.test(value);
});
jQuery.validator.addMethod('numericOnly', function(value, element){
	return /^[0-9]+$/.test(value);
});
