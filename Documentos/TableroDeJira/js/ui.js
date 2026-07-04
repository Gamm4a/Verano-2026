import {tasks} from "./state.js";

const list = document.getElementById("taskList");
export const render =(tasks) => {

    list.innerHTML = '';

    tasks.forEach(t => {
        const li = document.createElement('li');

        li.className = `task-item ${t.completed ? 'completed' : ''}`;

        li.dataset.id = t.id;

        const span = document.createElement('span');
        span.textContent = t.text;

        const actions = document.createElement("div");
        actions.className = 'actions';

        const btnEdit = document.createElement('button');
        btnEdit.className = 'btn-edit';
        btnEdit.textContent = "✎";

        const btnDelete = document.createElement('button');
        btnDelete.className = 'btn-delete';
        btnDelete.textContent = "X";

        actions.appendChild(btnEdit);
        actions.appendChild(btnDelete);

        li.appendChild(span);
        li.appendChild(actions);

        list.appendChild(li);

    })
}