<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <title>接口测试</title>
    <link rel="stylesheet" href="../static/css/index.css" />
    <script src="../static/js/jquery-3.2.1.js"></script>
    <script src="../static/js/layer/1.9.3/layer.js"></script>
</head>
<body>
    <div class="div_head">
        <label>请选择服务</label>
        <select class="select_one" id="serverName">
            <#list list as name>
                <option value="${name}">${name}</option>
            </#list>
        </select>

        <input class="button_one" type="button" value="扫描接口" onclick="scanInterfaceInfo();" />
        <input class="button_one" type="button" value="开始测试" onclick="autoTestInterface();" />
    </div>

    <div id="table_data" style="display: none;">
        <table border="0" cellspacing="0" cellpadding="0" >
            <thead>
                <tr style="height: 40px;">
                    <th colspan="4" class="th_title">
                        <label class="success_rec">成功: <a id="successCount" href="#" onclick="toggleData('success');"></a></label>
                        <label class="fail_rec">失败: <a id="failCount" href="#" onclick="toggleData('fail');"></a></label>
                    </th>
                </tr>
                <tr>
                    <th>请求地址</th>
                    <th>请求方式</th>
                    <th>请求结果</th>
                    <th>失败原因</th>
                </tr>
            </thead>

            <tbody id="testResult">

            </tbody>
        </table>
    </div>
</body>
<script>
    function scanInterfaceInfo() {
        var index = layer.load(0, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.ajax({
            url: "/scanInterfaceInfo",
            type: 'get',
            data: {"serverName":$("#serverName").val()},
            success: function(data) {
                layer.close(index);
                if(data.success){
                    layer.msg('扫描完成', {
                        icon: 6,
                        time: 1000
                    });
                }else{
                    layer.msg(data.msg, {
                        icon: 5,
                        time: 1000
                    });
                }
            },
            error: function(data) {
                layer.close(index);
                layer.msg(data.msg, {
                    icon: 5,
                    time: 1000
                });
            }
        });
    }

    var successList;
    var failList;
    function autoTestInterface() {
        var index = layer.load(0, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.ajax({
            url: "/autoTestInterface",
            type: 'get',
            data: {"serverName":$("#serverName").val()},
            success: function(data) {
                layer.close(index);
                if(data.success){
                    layer.msg('测试完成', {
                        icon: 6,
                        time: 1000
                    });

                    successList = data.successList;
                    failList = data.failList;
                    $("#testResult").html(getTestResult(data.list));
                    $("#successCount").html(successList.length);
                    $("#failCount").html(failList.length);
                    $("#table_data").show();
                }else{
                    $("#table_data").hide();
                    layer.msg(data.msg, {
                        icon: 5,
                        time: 1000
                    });
                }
            },
            error: function(data) {
                layer.close(index);
                layer.msg(data.msg, {
                    icon: 5,
                    time: 1000
                });
            }
        });
    }

    function toggleData(flag) {
        if(flag == 'success'){
            $("#testResult").html(getTestResult(successList));
        }else{
            $("#testResult").html(getTestResult(failList));
        }
    }

    function getTestResult(list) {
        var html = '';
        for(var i = 0; i < list.length; i++){
            html += '<tr>';
            html += '<td>' + list[i].url + '</td>';
            html += '<td>' + list[i].method + '</td>';
            html += '<td>' + list[i].result + '</td>';
            html += '<td>' + list[i].msg + '</td>';
            html += '</tr>';
        }
        return html;
    }
</script>
</html>