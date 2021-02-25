//Presence check takes an input string and checks that its length is greater than 0.
function presenceCheck(input) {
    return input.length > 0;
}

//Length check takes an input string as well as minimum and maximum length.
//Checks that the length of the input string is greater than or equal to the minimum length.
//Checks that the length of the input string is less than or equal to the maximum length.
function lengthCheck(input, min, max) {
    return (input.length >= min && input.length <= max);
}

//Range check takes an input number as well as minimum and maximum number.
//Checks that the length of the input number is greater than or equal to the minimum.
//Checks that the length of the input number is less than or equal to the maximum.
function rangeCheck(input, min, max) {
    return ((input > min) && (input < max));
}

//Email format check takes an input string as input.
//Checks that input is in the format xxx@xxx.xxx where xxx is a placeholder for substrings of any length.
function emailFormatCheck(input) {
    var i = 0;
    do {
        if (i === input.length) {
            return false;
        }
        i++;
    } while (input.charAt(i) != '@');
    do {
        i++;
        if (i === input.length) {
            return false;
        }
    } while (input.charAt(i) != '.');
    return true;
}

//Security check takes an input string and returns a boolean value which indicates whether the string is secure.
//A string is secure only if it contains at least one uppercase letter, lowercase letter, number and symbol.
function securityCheck(input) {
    var uppercase = false;
    var lowercase = false;
    var number = false;
    var symbol = false;
    for (var i = 0; i < input.length; i++) {
        if (/[A-Z]/.test(input.charAt(i))) {
            uppercase = true;
        }
        if (/[a-z]/.test(input.charAt(i))) {
            lowercase = true;
        }
        if (/[0-9]/.test(input.charAt(i))) {
            number = true;
        }
        if (/[-!$%^&*()_+|~=`{}\[\]:";'<>?,.\/]/.test(input.charAt(i))) {
            symbol = true;
        }
    }
    return uppercase && lowercase && number && symbol;
}

//Numeric type check takes an input string and returns a boolean that indicates whether the string contains a number.
//Uses regular expressions.
function numericTypeCheck(input) {
    return /^-?[0-9]\d*(\.\d+)?$/.test(input);
}

//Price format check takes an input string and returns a boolean that indicates whether the string contains a number to two decimal places.
function priceFormatCheck(input) {
    var i = 0;
    do {
        if (i === input.length) {
            return false;
        }
        i++;
    } while (input.charAt(i) != '.');
    return 2 === input.length - (i + 1);
}

function containsCharCheck(input, char) {
    for (var i = 0; i < input.length; i++) {
    	if (input.charAt(i) === char) {
    		return true;
    	}
    }
    return false;
}

//Date format checks takes an input value and returns a boolean value that indicates whether is in dd/mm/yyyy format.
function dateFormatCheck(input) {
	if (input.length != 10) {
		return false;
	}
    for (var i = 0; i < input.length; i++) {
        if (i === 2 || i === 5) {
            if (input.charAt(i) != "/") {
                return false;
            }
        } else {
            if (!/[0-9]/.test(input.charAt(i))) {
                return false;
            }
        }
    }
    var day = parseInt(input.substring(0,2));
    var month = parseInt(input.substring(3,5));
    var year = parseInt(input.substring(6,10));
   	if (month === 1 && day <= 31) {
   		return true;
   	}
   	if (year % 4 === 0) {
   		if (month === 2 && day <= 29) {
	   		return true;
	   	}
   	} else {
	   	if (month === 2 && day <= 28) {
	   		return true;
	   	}
	}
	if (month === 3 && day <= 31) {
   		return true;
   	}
   	if (month === 4 && day <= 30) {
   		return true;
   	}
   	if (month === 5 && day <= 31) {
   		return true;
   	}
   	if (month === 6 && day <= 30) {
   		return true;
   	}
   	if (month === 7 && day <= 31) {
   		return true;
   	}
   	if (month === 8 && day <= 31) {
   		return true;
   	}
   	if (month === 9 && day <= 30) {
   		return true;
   	}
   	if (month === 10 && day <= 31) {
   		return true;
   	}
   	if (month === 11 && day <= 30) {
   		return true;
   	}
   	if (month === 12 && day <= 31) {
   		return true;
   	}
   	return false;
}

//Double entry check recieves two input strings and returns a boolean value that indicates whether they are the same.
function doubleEntryCheck(input1, input2) {
    return input1 === input2;
}