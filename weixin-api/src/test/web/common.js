/**
 * 分享
 * @param shareFriendTitle 分享给朋友标题
 * @param shareFriendDesc 分享给朋友描述
 * @param shareQuanTitle 分享到朋友圈标题
 * @param shareImgUrl 分享图标
 * @param shareUrl 分享URL
 * @param afterReady 初始化之后的回调
 */
function initShare(shareFriendTitle, shareFriendDesc, shareQuanTitle, shareImgUrl, shareUrl, afterReady) {

    $.ajax({
        type: "get",
        url: "https://wx.edian.shop/mp/jsconfig/get?url=" + encodeURIComponent(window.location.href),
        dataType:"json",
        success: function(data) {
            if(data.ret == 1000) {
                wx.config( {
                    debug : false,
                    appId: data.model.appId,
                    timestamp: data.model.timestamp,
                    nonceStr: data.model.nonceStr,
                    signature: data.model.signature,
                    jsApiList : [
                        'checkJsApi',
                        'onMenuShareTimeline',
                        'onMenuShareAppMessage',
                        'onMenuShareQQ',
                        'onMenuShareWeibo'
                    ]
                });
            } else{
                alert(data.msg);
            }

        },
        error : function(err) {
            //alert("error");
        }
    });
    wx.error(function(res){

        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
        alert(res);
    });

    wx.ready(function () {
        changeShare(shareFriendTitle, shareFriendDesc, shareQuanTitle, shareImgUrl, shareUrl);
        if(afterReady != undefined) {
            afterReady();
        }

    });
}


/**
 * 分享
 * @param shareFriendTitle 分享给朋友标题
 * @param shareFriendDesc 分享给朋友描述
 * @param shareQuanTitle 分享到朋友圈标题
 * @param shareImgUrl 分享图标
 * @param shareUrl 分享URL
 */
function changeShare(shareFriendTitle, shareFriendDesc, shareQuanTitle, shareImgUrl, shareUrl) {

    wx.onMenuShareAppMessage({
        title: shareFriendTitle,
        desc: shareFriendDesc,
        link: shareUrl,
        imgUrl: shareImgUrl,
        success: function (res) {
            alert("success");
        },
        cancel: function (res) {
            alert("cancel");
        },
        fail: function (res) {
            alert("fail");
        }
    });
    wx.onMenuShareTimeline({
        title: shareQuanTitle,
        link: shareUrl,
        imgUrl: shareImgUrl,
        success: function (res) {
            alert("success");
        },
        cancel: function (res) {
            alert("cancel");
        },
        fail: function (res) {
            alert("fail");
        }
    });
}