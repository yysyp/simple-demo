<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
>
<head>
    <title>[(${uriName})]</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" th:href="@{/css/bootstrap.min.css}">
    <link rel="stylesheet" th:href="@{/css/jquery-ui-timepicker-addon.min.css}">
    <link rel="stylesheet" th:href="@{/jqueryui/jquery-ui.min.css}">
    <link rel="stylesheet" th:href="@{/css/crud.css}">

    <script th:src="@{/js/jquery-3.5.1.min.js}" ></script>
    <script th:src="@{/jqueryui/jquery-ui.min.js}"></script>
    <script th:src="@{/js/jquery-ui-timepicker-addon.min.js}"></script>
    <script th:src="@{/js/jquery-ui-timepicker-addon-i18n.min.js}"></script>
    <script th:src="@{/js/jquery.validate.min.js}"></script>
    <!--<script th:src="@{/js/localization/messages_zh.js}" type="text/javascript"></script>-->
</head>
<body>
<div th:replace="~{fragments/header::header}"></div>
<a th:href="@{/api/[(${moduleName})]/[(${uriName})]}">[List]</a>

<div class="content-root">

    <div class="form-container">
        <form id="createform" th:action="@{/api/[(${moduleName})]/[(${uriName})]/save}" method="POST"
              th:object="${[(${entityKey})]Model.[(${dtoKey})]}">

            <input type="hidden" name="id" th:value="*{id}">
            <table>
[# th:each="attr,attrStat:${entityAttrs}" ]
    <tr><td><label>[(${attr.get('name')})]:</label></td><td width="75%">[(${tfUtil.formItem(attr)})]<label> &nbsp;</label></td></tr>
[/]
                <tr><td colspan="2" style="text-align: center;"><input type="submit" style="width:150px;" value="Save"><label>&nbsp;</label></td></tr>
            </table>

        </form>
    </div>

</div>

<script th:inline="javascript">
/*<![CDATA[*/

$(function() {
    $("form").submit(function(e) {
        var docHeight = $(document).height();
        $('body').append('<div id="selft-widow-shadow"></div>');
        $('#selft-widow-shadow')
        .height(docHeight)
        .css({
        'opacity': .4,
        'position': 'absolute',
        'top': 0,
        'left': 0,
        'background-color': 'black',
        'width': '100%',
        'z-index': 9003
        });
        $('#selft-widow-shadow').focus();
        setTimeout(function(){$("#selft-widow-shadow").remove();}, 1000);
    });

    [# th:each="attr,attrStat:${entityAttrs}" ]
        [# th:if="${attr.get('type') eq 'Date'}"]
            $('#[(${attr.get('name')})]').datetimepicker({
                dateFormat: "yy-mm-dd",
                timeFormat: "hh:mm:ss"
            });
        [/]
    [/]

    $("#createform").validate();

});


/*]]>*/
</script>

<div th:replace="~{fragments/footer::footer}"></div>
</body>
</html>