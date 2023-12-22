import sys
import pandas as pd

def find_description(category, transaction_type, csv_file_path='python/dataset/data.csv'):
    df = pd.read_csv(csv_file_path)

    result = df[(df['category'] == category) & (df['type'] == transaction_type)]

    if not result.empty:
        return result['description'].iloc[0]
    else:
        return "No matching entry found."

if len(sys.argv) < 3:
    print("Usage: python script.py <category> <type>")
    sys.exit(1)

category = sys.argv[1]
transaction_type = sys.argv[2]

description = find_description(category, transaction_type)
print(description)
