import React from 'react';
import './Messages.css';
import axios from '../axios-objects';
import NavBar from '../Navbar/Navbar';
import MessageInfo from './MessageInfo';
import MessageReply from './MessageReply';



class Message extends React.PureComponent{


    constructor(){
        super();
        this.state = {
                userId: null, 
                message: [],
                from: '',
                meni: '',
                replyID: null,
                message2: [],
                buttapprove: true    
            };
    }
    
    componentDidMount = async() => {
        const token = sessionStorage.getItem('token');
        const response00 = await axios.get('/getLoggedUser', {
                headers: {
                    'Authorization' : 'Bearer ' + token
                }
            });
        if(response00) {
            console.log(response00);
            this.setState({userId: response00.data.id});
        }

       
        const response2 = await axios.get('/porukeza', {params:{id:this.state.userId}});
        if(response2){
            this.setState({message: response2.data});
        }

        console.log(this.state.message);
        const response3 = await axios.get('/porukeod', {params:{id:this.state.userId}});
        if(response3){
            console.log(response3.data);
            this.setState({message2: response3.data});
        }


    }

   
    processingMessage = async(idd, str) => {
        if(str === 'DELETE'){
            await axios.get('/del', {params: {id: idd}});
            window.location.reload();
        }
    }

    replyMessage = (id) => {
        this.setState({meni: 'REPLY', replyID: id});
    }

    redirecting = async(text , status) => {
        if(status==='BACK'){
            this.setState({meni: 'INBOX'});
        }
        if(status==='REPLY'){
            const data = {senderId: this.state.userId, receiverId: this.state.replyID, message: text};
            await axios.post('/send',data);
            window.location.reload();
        }
    }

    renderMeni(){
        if(this.state.meni==='INBOX'){
            this.setState({buttapprove: true});
            return this.state.message.map((m) =>{
                return (
                   <MessageInfo data={m} key={m.id} ret={this.processingMessage} ret2={this.replyMessage}/>
                );
            });
        }
        if(this.state.meni===''){
            this.setState({buttapprove: true});
            return(<div><h3><b>Please choose what messages you want to see</b></h3></div>);
        }
        if(this.state.meni==='SENT'){
            return this.state.message2.map((m) =>{
                return (
                   <MessageInfo data={m} key={m.id} ret={this.processingMessage} ret2={this.replyMessage}/>
                );
            });
        }
        if(this.state.meni==='REPLY'){
            this.setState({buttapprove: false});
            return(<MessageReply reply={this.state.replyID} actionn={this.redirecting}/>);
        }
        
    }

    send = (tip) => {
        this.setState({meni: tip});
    }

    renderButton(){
        if(this.state.buttapprove==true){
            return (
                <div style={{ justifyContent: 'center', alignItems: 'center', overflow:'scroll'}}>
                    <button className="button button3" onClick={()=>{this.setState({meni:'INBOX'})}}>Inbox</button>
                    <button className="button button3" onClick={()=>{this.setState({meni:'SENT'})}}>Sent</button>
                </div>
            );
        }
    }

    render(){
        return (
            <div>
                <NavBar />
                <header id="showcase">
                    <div className="containerSearch showcase-container">
                            {this.renderButton()}
                        <div style={{ justifyContent: 'center', alignItems: 'center', overflow:'scroll'}}>
                            {this.renderMeni()}
                        </div>
                    </div>
                </header>
            </div>
     
        );
    }

}

export default Message;