import csv

input_file = r"C:\Users\kisce\Desktop\Python\crb_detections_2026-04-11.csv"
output_file = r"C:\Users\kisce\OneDrive\Desktop\Python\crb_detections.kml"

kml_lines = [
    '<?xml version="1.0" encoding="UTF-8"?>',
    '<kml xmlns="http://www.opengis.net/kml/2.2">',
    '<Document>',
    '<name>CRB Damage Detections</name>',
    '<Style id="live"><IconStyle><color>ff00ff00</color><scale>1.2</scale></IconStyle></Style>',
    '<Style id="dead"><IconStyle><color>ff0000ff</color><scale>1.2</scale></IconStyle></Style>',
    '<Style id="vcut"><IconStyle><color>ff00ffff</color><scale>1.2</scale></IconStyle></Style>',
]

with open(input_file, 'r') as f:
    reader = csv.DictReader(f)
    for row in reader:
        lat = row['latitude']
        lon = row['longitude']
        label = row['label']
        conf = row['confidence']
        time = row['timestamp']
        
        # Skip rows with missing coordinates
        if not lat or not lon or lat == '' or lon == '':
            continue
        
        kml_lines.append('<Placemark>')
        kml_lines.append(f'<name>{label} ({conf})</name>')
        kml_lines.append(f'<description>Detected: {time}&lt;br/&gt;Confidence: {conf}</description>')
        kml_lines.append(f'<styleUrl>#{label}</styleUrl>')
        kml_lines.append(f'<Point><coordinates>{lon},{lat},0</coordinates></Point>')
        kml_lines.append('</Placemark>')

kml_lines.append('</Document>')
kml_lines.append('</kml>')

with open(output_file, 'w') as f:
    f.write('\n'.join(kml_lines))

print(f"Created {output_file}")