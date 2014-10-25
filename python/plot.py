import os
import pylab as pl

def main():
    file1 = parseData("matlab/precision.txt")
    pl.plot(file1[0], file1[1], label='precision')
    pl.legend()
    
    file2 = parseData("matlab/accuracy.txt")
    pl.plot(file2[0], file2[1], label='accuracy')
    pl.legend()
    
    file3 = parseData("matlab/recall.txt")
    pl.plot(file3[0], file3[1], label='recall')
    pl.legend()
    
    file4 = parseData("matlab/fMeasure.txt")
    pl.plot(file4[0], file4[1], label='fMeasure')
    pl.legend()
    
    pl.savefig("matlab/result.png")

def parseData(fileName):
    ins = open(fileName, "r" )
    num = []
    pre = []
    for line in ins:
        x, y = line.strip().split(' ')
        try:
            x = float(x)
            y = float(y)
        except ValueError:
            continue
        num.append(x)
        pre.append(y)
    ins.close()
    return [num, pre]

if __name__=="__main__":
    main()

