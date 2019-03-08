let encrypt = new JSEncrypt();
let publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAPCw5bm2KGBQN2pFH47u/AMYwE81Mhll\n" +
    "Tq+v+2YbJ/XW8wC9BCtJAtIuQws6sFT5PJfRuQIpO46Tze139XQnt58CAwEAAQ==";
encrypt.setPublicKey(publicKey);

$().ready(function () {
    console.log("ready");
    $("#form").submit(function (e) {
        // e.preventDefault();
        let nick = $("input[name=nickName]").val();
        let password = $("input[name=password]").val();
        let encryptNick = encrypt.encrypt(nick);
        let encryptPassword = encrypt.encrypt(password);
        $("input[name=nickName]").val(encryptNick);
        $("input[name=password]").val(encryptPassword);
    })
})