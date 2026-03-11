const form = document.getElementById("loginForm");

form.addEventListener("submit", function(event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    if(email === "" || senha === "") {
        alert("Preencha todos os campos!");
        return;
    }

    // Simulação de login
    console.log("Email:", email);
    console.log("Senha:", senha);

    alert("Login realizado com sucesso!");
});