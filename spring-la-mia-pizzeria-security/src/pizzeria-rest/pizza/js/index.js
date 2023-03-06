
index();

function index(){
    axios.get('http://localhost:8080/api/pizza').then((response) => {
        console.log('richiesta ok'. response);
        // svuoto la lista
        document.querySelector('#pizza-item').innerHTML = '';
        // console.log(response.data);
        response.data.forEach(pizza => {
            document.querySelector('#pizza-item').innerHTML +=
            `<li class="my-3">
                <a href="./show.html?id=${pizza.id}"> Vai al dettaglio </a>
                <h3> ${pizza.nome} </h3>
                <div> ${pizza.descrizione} </div>
                <img style="width: 500px" src="${pizza.foto}">
                <div> Prezzo: ${pizza.prezzo} €</div>
                <a class="btn btn-danger" onclick="deletePizza(${pizza.id})">
                        <i class="fa-solid fa-trash-can"> </i>
                </a>
                // <a class="btn btn-primary" href="./edit.html?id=${pizza.id}">
                    Modifica
                </a>
            </li>`;
        });;

        console.log(response.data)
    }).catch((response)=>{
        console.log('richiesta ko'. response)
    })
}

function deletePizza(pizzaId){
    const conferma = confirm('Sei sicuro di voler cancellare?');

    if(conferma){
        axios.delete('http://localhost:8080/api/pizza/' + pizzaId).then((response)=>{
      
            index()
        }).catch((response) => {
           
            alert('Errore durante la richiesta!');
        })
    }
}