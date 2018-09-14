$(function () {
    $userList = $("<tbody>");

    function request(onDone, str, method, data, dataType) {
        var BASE_URL = 'http://localhost:8080/users/find/';
        var url = str ? BASE_URL + str : BASE_URL;
        $.ajax({
            url: url,
            method: method || 'GET',
            data: JSON.stringify(data) || null,
            dataType: dataType || null,
            contentType: 'application/json',
            success: onDone
        })
    }

    function getUsers(search) {
        function onDone(response) {
            response.forEach(function (user) {
                var uId = user.id;
                var $tr = $('<tr>');
                $tr.append("<td>"+uId+"</td>")
                    .append("<td>"+user.firstName+" "+user.lastName+"</td>")
                    .append("<td>"+user.email+"</td>")
                    .append("<td>"+user.phone+"</td>")
                    .append("<td><a href='./add/"+uId+"' class='btn btn-primary'><i class='fa fa-plus fa-fw'></i></a></td>");
                $userList.append($tr)

            })
        }
        request(onDone, search)
    }

    var $form = $("#searchUser");
    var $table = $("#users");
    $table.append($userList);

    $form.on('keyup', 'input', function (event) {
        $userList.empty();
        var $searchform = $(this);
        var searchVal = $searchform.val();
        getUsers(searchVal)
    });

});