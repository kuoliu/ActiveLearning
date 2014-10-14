A = load('matlab\precision.txt');
B = load('matlab\recall.txt');
C = load('matlab\accuracy.txt');

figure(1)
plot(A(:,1),A(:,2), 'b');
hold on;
plot(B(:,1),B(:,2), 'r');
hold on;
plot(C(:,1),C(:,2), 'g');
legend('precision','recall', 'accuracy');
print(1,'-djpeg','line.jpeg')