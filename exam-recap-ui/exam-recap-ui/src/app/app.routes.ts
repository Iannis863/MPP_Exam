import {Routes} from '@angular/router';
import {App} from './app';
import {GameBoard} from './game/board.component';

export const routes: Routes = [
  {path: "main", component: App},
  {path: "user/:Uid/game", component: GameBoard},
];
