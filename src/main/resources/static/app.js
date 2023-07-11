$(async function () {
    await getTableWithUsers();
    getModal();
    createUser();
    userTable();
})


const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    allUsers: async () => await fetch('admin/users'),
    getUser: async (id) => await fetch(`admin/users/${id}`),
    createUser: async (user) => await fetch('admin/users', {
        method: 'POST',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }),
    updateUser: async (user, id) => await fetch(`admin/users/${id}`, {
        method: 'PUT',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }),
    deleteUser: async (id) => await fetch(`admin/users/${id}`, {method: 'DELETE', headers: userFetchService.head})
}

async function userTable() {
    let table = $('#usertable tbody');
    table.empty();

    let uid = document.getElementById("userid").innerHTML;
    let id = Number.parseInt(uid);
    let preuser = await userFetchService.getUser(id);
    let user = preuser.json();

    user.then(user => {
        let roles = [];
        (user.roles).forEach(role => {
            roles.push((" " + role.name))
        });

        let tableFill = `$(
                <tr>
                  
                     <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.password}</td>
                    <td>${user.surname}</td>
                    <td>${user.age}</td>
                     <td>${roles}</td>

                </tr>
            )`;
        table.append(tableFill);
    })
}

async function getTableWithUsers() {
    let table = $('#TableWithUsers tbody');
    table.empty();

    await userFetchService.allUsers()
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                let roles = [];
                (user.roles).forEach(role => {
                    roles.push((" " + role.name))
                })
                let tableFilling = `$(
                        <tr>
                      
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.password.slice(0, 15)}...</td>
                            <td>${user.surname}</td>
                            <td>${user.age}</td>                         
                            <td>${roles}</td>
                       <td> 
                                <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info btn-lg" 
                                data-toggle="modal" data-target="#someModal"></button>
                            </td>
                            <td>
                                <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-danger btn-lg" 
                                data-toggle="modal" data-target="#someModal"></button>
                            </td>
                        </tr>
                )`;
                table.append(tableFilling);
            })
        })

    // обрабатываем нажатие на  edit или delete
    // достаем  данные и отдаем модалке, которую  открываем
    $("#TableWithUsers").find('button').on('click', (event) => {
        let defaultModal = $('#someModal');

        let targetButton = $(event.target);
        let buttonUserId = targetButton.attr('data-userid');
        let buttonAction = targetButton.attr('data-action');

        defaultModal.attr('data-userid', buttonUserId);
        defaultModal.attr('data-action', buttonAction);
        defaultModal.modal('show');
    })
}


// выбор действия модалки
async function getModal() {
    $('#someModal').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        let thisModal = $(event.target);
        let userid = thisModal.attr('data-userid');
        let action = thisModal.attr('data-action');
        switch (action) {
            case 'edit':
                editUser(thisModal, userid);
                break;
            case 'delete':
                deleteUser(thisModal, userid);
                break;
        }
    }).on("hidden.bs.modal", (e) => {
        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}

// редактируем юзера в модалке
async function editUser(modal, id) {
    let preuser = await userFetchService.getUser(id);
    let user = preuser.json();

    modal.find('.modal-title').html('Edit user');

    let editButton = `<button  class="btn btn-outline-success" id="editButton">Edit</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(editButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(user => {
        let bodyForm = `
            <form class="form-group" id="editUser">
                                <label for="surname"><b>Id:</b> </label>

                <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled>
                                    <label for="surname"><b>Username:</b> </label>

                <input class="form-control" type="text" id="username" value="${user.username}">
                                    <label for="surname"><b>Password:</b> </label>

                <input class="form-control" type="text" id="password" value="${user.password}">
                                    <label for="surname"><b>Surname:</b> </label>

                <input class="form-control" type="text" id="surname" value="${user.surname}">
                                    <label for="surname"><b>Age:</b> </label>

                <input class="form-control" id="age" type="number" value="${user.age}">
                  <div class="container text-center">
                                                        <label class="col-4 col-form-label" for="nroles"><b>Roles:</b> </label>
                                                        <select class="custom-select" multiple id="nroles" name="roles" 
                                                                size="2">

                                                            <option value="2">Admin</option>
                                                            <option value="1">User</option>

                                                        </select>
                                                    </div>
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    })

    $("#editButton").on('click', async () => {
        let id = modal.find("#id").val().trim();
        let username = modal.find("#username").val().trim();
        let password = modal.find("#password").val().trim();
        let surname = modal.find("#surname").val().trim();
        let age = modal.find("#age").val().trim();
        let listRoles = [];
        const formEdit = document.getElementById('editUser');
        for (let i = 0; i < formEdit.nroles.options.length; i++) {
            if (formEdit.nroles.options[i].selected) {
                listRoles.push({
                    id: formEdit.nroles.options[i].value,
                    role: formEdit.nroles.options[i].text
                });
            }
        }
        let data = {
            id: id,
            username: username,
            password: password,
            surname: surname,
            age: age,
            roles: listRoles
        }
        const response = await userFetchService.updateUser(data, id);

        if (response.ok) {
            getTableWithUsers();
            modal.modal('hide');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="Error">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);
        }
    })
}

// удаляем юзера в модалке
async function deleteUser(modal, id) {
    let preuser = await userFetchService.getUser(id);
    let user = preuser.json();

    modal.find('.modal-title').html('Delete user');

    let deleteButton = `<button  class="btn btn-outline-danger" id="deleteButton">Execute</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(deleteButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(user => {
        let bodyForm = `
            <form class="form-group" id="editUser">
                <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled><br>
                <input class="form-control" type="text" id="username" value="${user.username}" disabled><br>
                <input class="form-control" type="text" id="password" value="${user.password}" disabled><br>
                <input class="form-control" type="text" id="surname" value="${user.surname}" disabled><br>
                <input class="form-control" id="age" type="number" value="${user.age}" disabled>
                  <div class="container text-center">
                                                        <label class="col-4 col-form-label"><b>Roles:</b> </label>
                                                        <select class="custom-select" multiple id="roles" name="roles" 
                                                                size="2" disabled>

                                                            <option value="2">Admin</option>
                                                            <option value="1">User</option>

                                                        </select>
                                                    </div>
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    })

    $("#deleteButton").on('click', async () => {
        await userFetchService.deleteUser(id);
        getTableWithUsers();
        modal.modal('hide');
    })
}

async function createUser() {
    $('#addNewUserButton').click(async () => {
        let addUserForm = $('#defaultSomeForm')
        let username = addUserForm.find('#newuserUsername').val().trim();
        let password = addUserForm.find('#newuserPassword').val().trim();
        let surname = addUserForm.find('#newuserSurname').val().trim();
        let age = addUserForm.find('#newuserAge').val().trim();
        let listRoles = [];
        const formEdit = document.getElementById('defaultSomeForm');
        for (let i = 0; i < formEdit.aroles.options.length; i++) {
            if (formEdit.aroles.options[i].selected) {
                listRoles.push({
                    id: formEdit.aroles.options[i].value,
                    role: formEdit.aroles.options[i].text
                });
            }
        }
        let data = {
            username: username,
            password: password,
            surname: surname,
            age: age,
            roles: listRoles
        }
        const response = await userFetchService.createUser(data);
        if (response.ok) {
            getTableWithUsers();
            addUserForm.find('#newuserUsername').val('');
            addUserForm.find('#newuserPassword').val('');
            addUserForm.find('#newuserSurname').val('');
            addUserForm.find('#newuserAge').val('');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="Error">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            addUserForm.prepend(alert)
        }
    })
}