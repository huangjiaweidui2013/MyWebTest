    layui.use('form', function(){
        var form = layui.form;
        form.render();
    });

    layui.use(['upload','jquery','element'], function(){
        var $ = layui.jquery
            ,upload = layui.upload
            ,element = layui.element;
        element.init();
        //拖拽上传
        upload.render({
            elem: '#test10'
            ,url: "/upload"
            //限制大小 5000M
            // ,size: 5000000
            ,auto: false//是否选完文件后自动上传
            ,bindAction: '#testListAction'//指向一个按钮触发上传
            ,method: 'post'
            //接收任何类型的文件
            ,accept: 'file'
            ,before: function (obj){
                getProgress(element);
            }
            //完成回调
            ,done: function(res){
                layer.msg(res.code);
                if (res.code){
                    layui.$('#uploadDemoView').removeClass('layui-hide').attr('src');
                }
                element.progress('demo','100%');
            }
        });
    });

    function getProgress(element) {
        $.ajax({
//            url: 'http://localhost:8080/progress',
            url: '/progress',
            type: 'post',
            dataType: 'json',
            success: function (data) {
                //方法中传入的参数data为后台获取的数据
                console.log(data);
                read = data.bytesRead;
                total = data.contentLength;
                var completePercent = Math.round(read / total * 100)+ '%';

                if (completePercent != 0) {
                    element.progress('demo', completePercent);
                }
                console.log(completePercent);
                $('#progress').attr("lay-percent", completePercent);
                if (read != total){
                    setTimeout(getProgress, 500, element);
                }
            }
        })
    }