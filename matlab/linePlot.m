A = load('matlab\precision.txt');
B = load('matlab\recall.txt');
C = load('matlab\accuracy.txt');
D = load('matlab\fMeasure.txt');

figure(1)
plot(A(:,1),A(:,2), 'b');
hold on;
plot(B(:,1),B(:,2), 'r');
hold on;
plot(C(:,1),C(:,2), 'g');
hold on;
plot(D(:,1),D(:,2), 'y');
legend('precision','recall', 'accuracy','F0.5');
xlabel('round');
ylabel('percentage');
title('Precision Recall Accuracy F0.5 Line Plot');
print(1,'-djpeg','line.jpeg')