import requests
from deep_translator import GoogleTranslator

CLE_API_RAWG = "fc6fc9da02854e66b8d2d0115d28c680"
EMAIL = "admin@gamelife.fr"
MOT_DE_PASSE = "Test123?!"
URL_AUTH = "http://localhost:8080/utilisateur/auth"
URL_ENDPOINT_BACK = "http://localhost:8080/api/v1/plateformes"

def obtenir_token():
    payload = {
        "email": EMAIL,
        "password": MOT_DE_PASSE
    }

    try:
        reponse = requests.post(URL_AUTH, json=payload)
        reponse.raise_for_status()
        return reponse.text
    except requests.exceptions.RequestException as e:
        print("Erreur lors de la requête HTTP pour obtenir le token d'accès :", e)
        return None

def obtenir_plateformes():
    url = "https://api.rawg.io/api/platforms"

    try:
        reponse = requests.get(url, params={"key": CLE_API_RAWG})
        reponse.raise_for_status()
        donnees = reponse.json()
        plateformes = [{"libelle": plateforme['name']} for plateforme in donnees['results']]
        return plateformes
    except requests.exceptions.RequestException as e:
        print("Erreur lors de la requête HTTP vers l'API RAWG :", e)
        return []

def envoyer_plateformes_vers_backend(plateforme, token):
    try:
        headers = {"Authorization": f"Bearer {token}"}
        reponse = requests.post(URL_ENDPOINT_BACK, json=plateforme, headers=headers)
        reponse.raise_for_status()
        print("Plateforme envoyée avec succès à l'endpoint back-end.")
    except requests.exceptions.RequestException as e:
        print("Erreur lors de la requête HTTP vers l'endpoint back-end :", e)

if __name__ == "__main__":
    token = obtenir_token()
    if token:
        plateformes = obtenir_plateformes()
        if plateformes:
            for plateforme in plateformes:
                envoyer_plateformes_vers_backend(plateforme, token)
        else:
            print("Aucune plateforme trouvée.")
    else:
        print("Impossible d'obtenir le token d'accès.")