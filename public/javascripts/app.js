var that;
var withOptions = ["RADIO_BUTTON", "CHECK_BOX", "COMBO_BOX"];
$('#datetimepicker1').datetimepicker();
$('#ex1').slider({
    formatter: function (value) {
        return 'Current value: ' + value;
    }
});
$('button#confirm').click(function (e) {
    $('#confirm-delete').modal('hide');
    $.ajax({
        type: 'POST',
        url: $('#confirm-delete').attr('href'),
        success: function (data) {
            that.closest('tr').remove();
        }
    });
});
$('a#deleteField').click(function (e) {
    that = $(this);
    $('#confirm-delete').modal('show').attr('href', $(this).attr('href'));
    return false;
});
$(function () {
    $(document).on('click', '.btn-add', function (e) {
        e.preventDefault();

        var controlForm = $('.controls'),
            currentEntry = $(this).parents('.entry:first'),
            newEntry = $(currentEntry.clone()).appendTo(controlForm);

        newEntry.find('input').val('');
        controlForm.find('.entry:not(:last) .btn-add')
            .removeClass('btn-add').addClass('btn-remove')
            .removeClass('btn-success').addClass('btn-danger')
            .html('<i class="fa fa-minus-circle"></i>');
    }).on('click', '.btn-remove', function (e) {
        $(this).parents('.entry:first').remove();

        e.preventDefault();
        return false;
    });
});
$(document).on('change', '.opts', function () {
    var requiredCheckboxes = $(this).closest('fieldset').find(':checkbox[required]');
    var notRequiredCheckboxes = $(this).closest('fieldset').find(':checkbox');
    var booleanVal = false;
    notRequiredCheckboxes.each(function(i,requiredField) {
        if($(requiredField).prop('checked')) {
            booleanVal = true;
        }
    })
    if (booleanVal) {
        requiredCheckboxes.removeAttr('required');
    } else {
        notRequiredCheckboxes.attr('required', 'required');
    }
});


$(document).on('change', '#types', function () {
    getUrlLastParam();
    if (contains.call(withOptions, $(this).val())) {
        $('#options').show();
        $('.options').show();
        $('.options').prop('required',true);
        $(".options").prop('disabled', false);
        $(".sliderOptions").remove();
        removeErrors();
    } else if ($(this).val()==="SLIDER") {
        $('#options').hide();
        $(".options").hide();
        $('.options').prop('required',false);
        $(".options").prop('disabled', true);
        var innerHtml = "<div class=\"sliderOptions\"> <div class=\"entry input-group col-xs-3\">"+
            "<input class=\"form-control options\" name=\"fields\" type=\"text\" placeholder=\"Insert min value\" required=\"\">"+
            "<input class=\"form-control options\" name=\"fields\" type=\"text\" placeholder=\"Insert max value\" required=\"\"></div> </div>";
        $('#lastRow').after(innerHtml);
        removeErrors();
    }
    else {
        $('#options').hide();
        $(".options").hide();
        $('.options').prop('required',false);
        $(".options").prop('disabled', true);
        $(".sliderOptions").remove();
        removeErrors();
    }
});
$(document).on('click', '#reset', function () {
    $(':input', '#fieldCreate')
        .not(':button, :submit, :reset, :hidden')
        .val('')
        .removeAttr('checked')
        .removeAttr('selected');
    $(".options").prop('disabled', true);
    $('#options').hide();
    $(".sliderOptions").remove();
    removeErrors();
});
$(document).on('click', '#clearForm', function () {
    $(':input', '#respForm')
        .not(':button, :submit, :reset, :hidden')
        .val('')
        .removeAttr('checked')
        .removeAttr('selected');
    $(".options").prop('disabled', true);
    $('#options').hide();
    removeErrors();
});
$(document).on('submit', '#fieldCreate form', function (e) {
    e.preventDefault();
    removeErrors();
    var result = {};
    result.fieldId = getUrlLastParam();
    result.label = $('input[name=label]').val();
    result.type = $('select[name=type]').val();
    result.required = $('input[name=required]').prop("checked");
    result.isActive = $('input[name=active]').prop("checked");
    var options = [];
    $('input[name="fields"]').each(function (i) {
        if (!($(this).val() === "")) {
            var option = {};
            option.optionValue = $(this).val();
            options.push(option);
        }
    });
    result.options = options;
    var func = function () {
        window.location.href = "/fields";
    }
    sendRequest("/field/update/", JSON.stringify(result), func)
});
$(document).on('submit', '#respForm form', function (e) {
    e.preventDefault();
    removeErrors();
    var object = $('form').serialize();
    var func = function () {
        $(".container").empty();
        $(".container").append("<h1 style=\"margin:25%;line-height:51px;vertical-align:middle;\">Thank you for submitting your data</h1>");
    }
    $.ajax({
        method: 'POST',
        url: "/response/submit",
        data: object,
    }).then(function successCallback(response) {
        func(response);
    }, function errorCallback(response) {
        setFieldErrors(response.responseText);
    });
});

function sendRequest(urlPattern, dataObj, myFunc) {
    $.ajax({
        method: 'POST',
        url: urlPattern,
        data: dataObj,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(function successCallback(response) {
        myFunc(response);
    }, function errorCallback(response) {
        setFieldErrors(response.responseText);
    });
}

function getUrlLastParam() {
    var href = window.location.href;
    return (href.substr(href.lastIndexOf('/') + 1));
}
var contains = function (needle) {
    var findNaN = needle !== needle;
    var indexOf;
    if (!findNaN && typeof Array.prototype.indexOf === 'function') {
        indexOf = Array.prototype.indexOf;
    } else {
        indexOf = function (needle) {
            var i = -1, index = -1;
            for (i = 0; i < this.length; i++) {
                var item = this[i];
                if ((findNaN && item !== item) || item === needle) {
                    index = i;
                    break;
                }
            }
            return index;
        };
    }
    return indexOf.call(this, needle) > -1;
};
function removeErrors() {
    $('.form-group').removeClass('has-error');
    $('.help-block').text('');
}
function setFieldErrors(message) {
    var errors = JSON.parse(message);
    errors.forEach(function(item,i,array){
        var field = $("[name=\""+item.fieldName+"\"]");
        $(field).closest('.form-group').addClass('has-error');
        var element = $(field).closest('.form-group').find('#errorMessage');
        element.text(element.text() + " " + item.message);
    });
}