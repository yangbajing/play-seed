jQuery(function () {

    jQuery('#signin-form').on('submit', function (evt) {
        evt.preventDefault();
        var thiz = jQuery(this);
        var payload = PS.form.serialize(thiz);
        payload.password = PS.util.sha1Hex(payload.password);

        PS.ajax.post('/inapi/user/signin', payload, 'json')
            .done(function (data, status, jqXhr) {
                setTimeout(function () {
                    window.location.href = '/home';
                }, 2000);
                thiz.find('.text-info').html('登录成功，2秒后跳转');
            })
            .fail(function (data, status, jqXhr) {
                alert('登录失败');
            });
    });

    jQuery('#signup-form').on('submit', function (evt) {
        evt.preventDefault();
        var thiz = jQuery(this);
        var payload = PS.form.serialize(thiz);
        if (payload.password.length < 6 || payload.password.length > 32) {
            alert('密码为6至32个字符');
            return;
        }
        if (payload.password !== payload.passwordRepeat) {
            alert('两次密码不匹配');
            return;
        }
        payload.password = PS.util.sha1Hex(payload.password);
        payload.passwordRepeat = undefined;

        PS.ajax.post('/inapi/user/signup', payload, 'json')
            .done(function (data, status, jqXhr) {
                setTimeout(function () {
                    window.location.href = '/account/signin';
                }, 2000);
                thiz.find('.text-info').html('注册成功，2秒后跳转到登录页面');
            })
            .fail(function (data, status, jqXhr) {
                alert('注册失败');
            });
    })

});
