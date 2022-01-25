    var fileBtn = $("input[name=file]");
    var processBar= $("#progressBar");

//点击确认导入执行此方法
function bulkImportChanges() {
    //获取批量操作状态文件
    var fileObj = fileBtn.get(0).files[0];
    var formData = new FormData();
    formData.append("file",fileObj);
    $.ajax({
        type : 'post',
        url : "/upload",
        data : formData,
        processData : false,      //文件ajax上传要加这两个的，要不然上传不了
        contentType : false,      //
        success : function(obj) {
            //导入成功
            if (obj.data == "success") {
                //定时任务获取redis导入修改进度
                var progress = "";
                var read = 0;
                var total = 1;
                var timingTask = setInterval(function(){
                    $.ajax({
                        type: 'post',
                        url: "/progress",
                        dataType : 'json',
                        success: function(result) {
                        read = result.bytesRead;
                        total = result.contentLength;

                        var completePercent = Math.round(read / total * 100)
                                                + '%';
                                        processBar.width(completePercent);
                                        processBar.text(completePercent);

//                            progress = result.value;
//                            if (progress != "error"){
//                                var date = progress.substring(0,6);
//                                //这里更新进度条的进度和数据
//                                $(".progress-bar").width(parseFloat(date)+"%");
//                                $(".progress-bar").text(parseFloat(date)+"%");
//                            }
                        }
                    });
                    //导入修改完成或异常（停止定时任务）
                    if (read == total) {
                        //清除定时执行
                        clearInterval(timingTask);
//                        $.ajax({
//                            type: 'post',
//                            url: "/risk/de***ess",
//                            dataType : 'json',
//                            success: function(result) {
//                                $("#bulkImportChangesProcessor").hide();
//                                if (parseInt(progress) == 100) {
//                                    alert("批量导入修改状态成功");
//                                }
//                                if (progress == "error") {
//                                    alert("批量导入修改状态失败");
//                                }
//                                //获取最新数据
//                                window.location.href="/risk/re***ByParam";
//                            }
//                        });
                    }
                }, 500)
            }else {
                $("#bulkImportChangesProcessor").hide();
                alert(obj.rspMsg);
                window.location.href="/risk/re***ByParam";
            }
        }
    });
}