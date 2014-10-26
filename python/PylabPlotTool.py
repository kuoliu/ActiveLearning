#!/usr/bin/python
import pylab
import sys

class PlotTool :
    '''
    Pylab mini plot tool to draw lines' pictures
    '''
    def __init__(self, baseConfig) :
        self.basecolors = ['red', 'green', 'yellow', 'blue', 'black', 'cyan', 'magenta']
        self.basemarkers = ['D', 'o', 'v', '+', 's', 'p', 'x']
        
        self.figsize = baseConfig.get('figsize', None)
        self.axis = baseConfig.get('axis', None)
        self.title = baseConfig.get('title', 'NoName')
        self.xlabel = baseConfig.get('xlabel', 'NoName')
        self.ylabel = baseConfig.get('ylabel', 'NoName')
        self.grid = baseConfig.get('grid', False)
        self.xaxis_locator = baseConfig.get('xaxis_locator', None)
        self.yaxis_locator = baseConfig.get('yaxis_locator', None)
        self.legend_loc = baseConfig.get('legend_loc', 0)
        
        if self.figsize != None :
            pylab.figure(figsize=self.figsize)
        if self.axis != None :
            pylab.axis(self.axis)
        
        pylab.title(self.title)
        pylab.xlabel(self.xlabel)
        pylab.ylabel(self.ylabel)
        ax = pylab.gca()
        pylab.grid(self.grid)
        if self.xaxis_locator != None :
            ax.xaxis.set_major_locator(pylab.MultipleLocator(self.xaxis_locator))
        if self.yaxis_locator != None :
            ax.yaxis.set_major_locator(pylab.MultipleLocator(self.yaxis_locator))
            
        self.lineList = []
        self.id = 1

    def addline(self, lineConf) :
        self.lineList.append((self.id, lineConf))
        self.id += 1
        return {'id' : self.id - 1}

    def __parselineConf(self, lineConf) :
        X = lineConf['X']
        Y = lineConf['Y']
        marker = lineConf.get('marker', None)
        color = lineConf.get('color', None)
        markerfacecolor = lineConf.get('markerfacecolor', color)
        label = lineConf.get('label', 'NoName')
        linewidth = lineConf.get('linewidth', 1)
        linestyle = lineConf.get('linestyle', '-')
        return X, Y, marker, color, markerfacecolor, label, linewidth, linestyle

    def plot(self) :
        colors = [self.basecolors[i % len(self.basecolors)] for i in range(len(self.lineList))]
        markers = [self.basemarkers[i % len(self.basemarkers)] for i in range(len(self.lineList))]
#         print len(self.lineList)
        
        for i in range(len(self.lineList)) :
#             print self.lineList[i]
            id, conf = self.lineList[i]
            if conf.get('color', None) == None:
                conf['color'] = colors[i]
#                 conf['markerfacecolor'] = colors[i]
#             if conf.get('marker', None) == None:
#                 conf['marker'] = markers[i]
            X, Y, marker, color, markerfacecolor, label, linewidth, linestyle = self.__parselineConf(conf)
            pylab.plot(X, Y, marker=marker, color=color, markerfacecolor=markerfacecolor, label=label, linewidth=linewidth, linestyle=linestyle)
        pylab.legend(loc=self.legend_loc)

    def show(self) :
        pylab.show()
        
    def save(self, outputFileName) :
        pylab.savefig('output/' + outputFileName + '.png')
        
    def parseData(self, fileName) :
        ins = open(fileName, "r")
        X = []
        Y = []
        for line in ins :
            first, second = line.strip().split(' ')
            try :
                first = float(first)
                second = float(second)
            except ValueError :
                continue
            X.append(first)
            Y.append(second)
        ins.close()
        return [X, Y]

        
if __name__ == '__main__' :
    '''
    basic configuration
    '''
    baseConfig = {
        'title' : sys.argv[2],
        'xlabel' : 'Total Annotation Cost',
        'ylabel' : 'Accuracy',
        'grid' : True,
        # 'figsize' : (6,8),
        # 'axis': [0,10,0,10],
        # 'xaxis_locator' : 0.5,
        # 'yaxis_locator' : 1,
        'legend_loc' : 'upper right'
    }
    
    tool = PlotTool(baseConfig)
    
    fileNames = [sys.argv[2 * i + 3] for i in range((len(sys.argv) - 3) / 2)]
    fileLabels = [sys.argv[2 * i + 4] for i in range((len(sys.argv) - 3) / 2)]
    
#     lineConf = {
#         'X' : X,
#         'Y' : Y
#         # 'marker' : 'x',
#         # 'color' : 'b',
#         # 'markerfacecolor' : 'r',
#         # 'label' : '222',
#         # 'linewidth' : 3,
#         # 'linestyle' : '--'
#     }
    
    for i in range(len(fileNames)) :
        X, Y = tool.parseData('output/' + fileNames[i])
        lineConf = {'X' : X, 'Y' : Y, 'label' : fileLabels[i]}
        tool.addline(lineConf)
        
    tool.plot()
#     tool.show()
    tool.save(sys.argv[1])
    
