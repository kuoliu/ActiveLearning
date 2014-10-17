A = load('matlab\precision.txt');
B = load('matlab\recall.txt');
C = load('matlab\accuracy.txt');

figure(2)
bar([A(:,1),A(:,2), B(:,2),C(:,2)])
legend('precision','recall', 'accuracy');
print(2,'-djpeg','bar.jpeg')