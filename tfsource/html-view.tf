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

<a th:href="@{/api/[(${moduleName})]/[(${uriName})]}">GoTo List</a>
<h3>View:</h3>
<div class="content-root">
    <div class="form-container">
    <form id="viewform" th:object="${[(${entityKey})]Model.[(${dtoKey})]}">
    <table>
    <tr><td><label>id:</label></td><td width="75%"><span th:text="*{id}" ></span><label> &nbsp;</label></td></tr>
    [# th:each="attr,attrStat:${entityAttrs}" ]
    <tr><td><label>[(${attr.get('name')})]:</label></td><td width="75%"><span th:text="*{[(${attr.get('name')})]}" ></span><label> &nbsp;</label></td></tr>
    [/]
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
        setTimeout(function(){$("#selft-widow-shadow").remove();}, 3000);
    });

});
/*]]>*/
</script>
<div th:replace="~{fragments/footer::footer}"></div>
</body>
</html>