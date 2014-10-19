A = load('matlab\precision.txt');
B = load('matlab\recall.txt');
C = load('matlab\accuracy.txt');
D = load('matlab\fMeasure.txt');

figure(2)
bar([A(:,1),A(:,2), B(:,2),C(:,2), D(:,2)])
legend('precision','recall', 'accuracy','F0.5');
print(2,'-djpeg','bar.jpeg')