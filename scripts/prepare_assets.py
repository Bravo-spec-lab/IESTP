import os,urllib.request
from pathlib import Path
p=Path("app/src/main/assets");p.mkdir(parents=True,exist_ok=True)
base="https://iestpantenororrego.edu.pe/website/wp-content/uploads/2025/04/"
urls={
 "logo.jpg":["https://www.iestpantenororrego.edu.pe/IMG/1.jpg"],
 "campus.jpg":[base+"antenor.jpg",base+"IEST-ANTENOR-.png"],
 "1.jpg":[base+"WhatsApp-Image-2025-04-14-at-9.05.02-AM.jpeg"],
 "2.jpg":[base+"WhatsApp-Image-2025-04-14-at-9.05.04-AM.jpeg"],
 "3.jpg":[base+"WhatsApp-Image-2025-04-14-at-9.05.03-AM.jpeg"],
 "4.jpg":[base+"WhatsApp-Image-2025-04-14-at-9.05.03-AM-2.jpeg"],
 "5.jpg":[base+"WhatsApp-Image-2025-04-14-at-9.05.03-AM-3.jpeg"],
 "6.jpg":[base+"WhatsApp-Image-2025-04-14-at-9.05.03-AM-1.jpeg"],
 "7.jpg":[base+"WhatsApp-Image-2025-04-14-at-9.05.04-AM-1.jpeg"]
}
for name,candidates in urls.items():
 success=False
 for url in candidates:
  try:
   req=urllib.request.Request(url,headers={"User-Agent":"Mozilla/5.0 (compatible; Institutional-Android-Build/1.0)"})
   data=urllib.request.urlopen(req,timeout=30).read()
   if len(data)<3000 or not (data.startswith(b"\xff\xd8") or data.startswith(b"\x89PNG")):raise ValueError("Formato no válido")
   (p/name).write_bytes(data);print("OK",name,len(data));success=True;break
  except Exception as e:print("FALLBACK",name,type(e).__name__,str(e)[:150])
 if not success:
  if name=="logo.jpg":raise RuntimeError("Falta logo institucional")
  print("AVISO sin fotografía para",name,"; la app mostrará su tarjeta textual")
