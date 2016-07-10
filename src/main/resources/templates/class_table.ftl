<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>

<#list classResourceList as classResource>
<div class="table-wrap">
    <table class="confluenceTable">
        <tbody>
        <tr>
            <td class="confluenceTd" colspan="6">${classResource.name}(${classResource.className})</td>
        </tr>
        <tr>
            <th class="confluenceTh"><strong>字段名</strong></th>
            <th class="confluenceTh"><strong>字段类型</strong></th>
            <th class="confluenceTh"><strong>必填</strong></th>
            <th class="confluenceTh"><strong>支持非必填形式</strong></th>
            <th class="confluenceTh"><strong>边界说明</strong></th>
            <th class="confluenceTh"><strong>说明</strong></th>
        </tr>
            <#list classResource.propertySourceList as propertySource>
            <tr>
                <td class="confluenceTd">${propertySource.propertyName}</td>
                <td class="confluenceTd">${propertySource.type}</td>
                <td class="confluenceTd">${propertySource.required}</td>
                <td class="confluenceTd"><#if propertySource.required == 'N'>支持null</#if></td>
                <td class="confluenceTd">${propertySource.validatorComment}</td>
                <td class="confluenceTd">${propertySource.propertyComment}</td>
            </tr>
            </#list>
        </tbody>
    </table>
</div>
</#list>
</body>
</html>