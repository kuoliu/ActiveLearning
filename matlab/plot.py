import os
import pylab as pl

def main():
    file1 = parseData("matlab/precision.txt")
    pre_x = file1[0]
    pre_y = file1[1]
    pl.plot(pre_x, pre_y, label='precision')
    pl.legend()
    
    file1 = parseData("matlab/accuracy.txt")
    pre_x = file1[0]
    pre_y = file1[1]
    pl.plot(pre_x, pre_y, label='accuracy')
    pl.legend()
    
    file1 = parseData("matlab/recall.txt")
    pre_x = file1[0]
    pre_y = file1[1]
    pl.plot(pre_x, pre_y, label='recall')
    pl.legend()
    
    file1 = parseData("matlab/fMeasure.txt")
    pre_x = file1[0]
    pre_y = file1[1]
    pl.plot(pre_x, pre_y, label='fMeasure')
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

