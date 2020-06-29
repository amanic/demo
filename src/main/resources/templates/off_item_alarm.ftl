<html>
<head>
</head>
<body>
<b>需下架的副条码明细如下：</b><br/>
<table border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
    <tr height="20" align="center">
        <td style="border: 0.5pt solid windowtext; padding-top: 1px; padding-right: 1px; padding-left: 1px; vertical-align: bottom; white-space: nowrap; background-color: rgb(141, 180, 226);">需下架的副条码</td>
        <td style="border: 0.5pt solid windowtext; padding-top: 1px; padding-right: 1px; padding-left: 1px; vertical-align: bottom; white-space: nowrap; background-color: rgb(141, 180, 226);">关联的主条码</td>
        <td style="border: 0.5pt solid windowtext; padding-top: 1px; padding-right: 1px; padding-left: 1px; vertical-align: bottom; white-space: nowrap; background-color: rgb(141, 180, 226);">副条码对应商品信息</td>
    </tr>
    <#list orderList! as order>
        <tr align="center">
            <td style="border: 0.5pt solid windowtext; white-space: nowrap;">${order.gmtModifiedStr!''}</td>
            <td style="border: 0.5pt solid windowtext; white-space: nowrap;">${order.gmtCreateStr!''}</td>
            <td style="border: 0.5pt solid windowtext; white-space: nowrap;">${order.title!''}</td>
        </tr>
    </#list>
</table>
</body>
</html>