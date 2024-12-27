function populateModal(button) {
    const id = button.getAttribute("data-id");
    const form = document.getElementById("mangaForm");
    console.log("Data ID:", id);
    if (id) {
        fetch(`/manga/get/${id}`)
            .then(response => response.json())
            .then(manga => {
                document.getElementById("title").value = manga.title || "";
                document.getElementById("author").value = manga.author || "";
                document.getElementById("price").value = manga.price || "";
                document.getElementById("state").value = manga.state || "FINISHED";
                document.getElementById("volume").value = manga.volume || 1;
                document.getElementById("cover_image").value = manga.cover_image || "";
                document.getElementById("description").value = manga.description || "";
                document.getElementById("mangaFormModalLabel").textContent = "Edit Manga";

                form.setAttribute("action", `/admin/edit/${id}`);
                form.setAttribute("method", "post");
                // Add hidden _method field for PUT
                if (!form.querySelector('input[name="_method"]')) {
                    const methodInput = document.createElement("input");
                    methodInput.setAttribute("type", "hidden");
                    methodInput.setAttribute("name", "_method");
                    methodInput.setAttribute("value", "put");
                    form.appendChild(methodInput);
                }
            })
            .catch(error => console.error('Error:', error));
    } else {
        document.getElementById("title").value = "";
        document.getElementById("author").value = "";
        document.getElementById("price").value = "";
        document.getElementById("state").value = "FINISHED";
        document.getElementById("volume").value = 1;
        document.getElementById("mangaFormModalLabel").textContent = "Add New Manga";

        form.setAttribute("action", "/admin/save");
        form.setAttribute("method", "post");

        const methodInput = form.querySelector('input[name="_method"]');
        if (methodInput) {
            form.removeChild(methodInput);
        }
    }
}
