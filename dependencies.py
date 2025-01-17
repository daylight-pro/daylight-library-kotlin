import os
import sys

argv = os.sys.argv

with open(argv[1], 'r') as f:
    lines = f.readlines()
    for line in lines:
        if line.startswith('import '):
            print('src/' + line.split(' ')[1].replace('.', '/')[0:-1] + '.kt')
